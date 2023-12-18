package it.unifi.ing.stlab.empedocle.scripts.reconciliator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.manager.PatientManager;
import it.unifi.ing.stlab.patients.model.Address;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.patients.model.Sex;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

//XXX to be tested
//@Startup
@Singleton
@TransactionManagement( TransactionManagementType.BEAN )
public class OfflinePatientReconciliator {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Resource
	private UserTransaction utx;	
	
	@EJB
	private PatientDao patientDao;		
	
	@EJB
	private UserDao userDao;
	
	@Inject
	private Logger logger;
	
	private MySQLConnector connector;
	
	private static final String DB_ADDRESS = "jdbc:mysql://localhost/empdb_dupl_patients";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	@PostConstruct
	@Remove
	public void init() {
        logger.info( "*** OfflinePatientReconciliator started ***" );
        initConnection();
        doJob();
	}
	
	@PreDestroy //XXX non viene distrutto
	public void destroy() {
		closeConnection();
		logger.info("*** OfflinePatientReconciliator stopped ***");
	}
	
	private void initConnection() {
        connector = new MySQLConnector( DB_ADDRESS, DB_USER, DB_PASS );
	}
	
	private void doJob() {
		try {
			ResultSet rs = connector.executeQuery( "SELECT * from patients WHERE NOT master" );
			int size = connector.count( rs );
			logger.info( "Detected " + size + " patients to be merged!" );
			
			int current = 1;
			int merged = 0;
			while ( rs.next() ) {
				logger.info( "Started " + current + "/" + size + " patients" );
	
				utx.begin();
			
				String slaveIdentifier = rs.getString( "id_ACE" );
				String masterIdentifier = rs.getString( "master_id_ACE" );
	
				Patient slave = patientDao.findByIdentifier( slaveIdentifier );
				Patient master = patientDao.findByIdentifier( masterIdentifier );
	
				PatientManager patientManager = new PatientManager();
				User author = userDao.findByUsername( "administrator" );
				Time time = new Time( new Date() );
				
				if ( master == null ) {
					// master not found
					if ( slave == null ) {
						// master and slave not found: patient not managed by empedocle
						logger.error( "Cannot find the patients you wish to merge" );
					} else {
						// slave found, master not found
	
						// 1. a new patient with role master is created
						master = patientManager.createPatient( author, time );
						update( master, rs );
						entityManager.persist( master );
	
						// 2. master and slave are merged
						Patient result = patientManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				} else {
					// master found
					
					// 1. master is updated
					Patient copy = patientManager.modify( author, time, master );
					update( copy, rs );
					
					Patient purged = patientManager.purge( copy );
					if ( purged != null ) {
						master = purged;
						entityManager.persist( master );
						
						//XXX per ora i riferimenti agli appointment rimangono sempre sullo stesso paziente
						// updateAppointmentsReferences();
					}
					
					if ( slave != null ) {
						// 2. master and slave are merged
						Patient result = patientManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				}
				utx.commit();
				logger.info( "Ended " + current + "/" + size + " patients" );
				current++;
			}
			rs.close();
			logger.info( "Merged  " + merged + "/" + size + " patients" );
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
			} catch (Exception ute) {
				logger.error( ute );
			}
		}
	}
	
	private void update( Patient patient, ResultSet rs ) throws SQLException {
		
		PatientIdentifier identifier = retrievePatientIdentifier( rs.getString( "master_id_ACE" ) );
		patient.setIdentifier( identifier );
		
		patient.setTaxCode( check( rs.getString( "master_tax_code" ) ) );
		patient.setSsnCode( check( rs.getString( "master_ssn_code" ) ) );
		patient.setName( check( rs.getString( "master_name" ) ) );
		patient.setSurname( check( rs.getString( "master_surname" ) ) );
		patient.setSex( Sex.valueOf( check( rs.getString( "master_sex" ) ) ) );
		patient.setBirthDate( rs.getDate( "master_birth_date" ) ) ;
		patient.setBirthPlace( check( rs.getString( "master_birth_place" ) ) );

		if ( check( rs.getString( "master_residence_place" ) ) != null ) {
			patient.setResidence( new Address() );
			patient.getResidence().setPlace( rs.getString( "master_residence_place" ) );
		}
		
		if ( check( rs.getString( "master_domicile_place" ) )  != null ) {
			patient.setDomicile( new Address() );
			patient.getDomicile().setPlace( rs.getString( "master_domicile_place" ) );
		}
		
		patient.setHomePhone( check( rs.getString( "master_home_phone" ) ) );
		patient.setWorkPhone( check( rs.getString( "master_work_phone" ) ) );
		patient.setNationality( check( rs.getString( "master_nationality" ) ) );
	}
	
	private String check( String value ) {
		return ( value != null && !value.isEmpty() ) ? value : null;
	}

	private PatientIdentifier retrievePatientIdentifier( String code ){
		PatientIdentifier identifier = null;
		
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<PatientIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from PatientIdentifier pi " +
									" where pi.code = :code ", 
									PatientIdentifier.class )
							.setParameter( "code", code )
							.setFlushMode( FlushModeType.COMMIT )
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 ) {
			identifier = PatientFactory.createPatientIdentifier();
			identifier.setCode( code );
			
		} else {
			identifier = results.get( 0 );
			entityManager.lock( identifier, LockModeType.OPTIMISTIC_FORCE_INCREMENT );
		}
			
		return identifier;
	}

	private void closeConnection() {
		connector.close();
	}
	
	class MySQLConnector {
		
		private Connection connection;
		
		MySQLConnector( String url,  String user, String password ) {
			try {
				connection = DriverManager.getConnection( url, user, password );
				logger.info( "Connesso con successo!" );
			} catch (SQLException e) {
				logger.error( "Errore", e );
				throw new RuntimeException( e );
			}
		}
		
		int count( ResultSet rs ) {
			int size = 0;
			try {
				rs.last();
			    size = rs.getRow();
			    rs.beforeFirst();
			}
			catch(Exception ex) {
			    return 0;
			}
			return size;
		}

		ResultSet executeQuery( String query ) {
			try {
				Statement stmt = connection.createStatement();

				logger.info( query );

				return stmt.executeQuery( query );
			} catch (SQLException e) {
				logger.error( "Errore", e );
				throw new RuntimeException( e );
			}
		}
		
		int executeUpdate( String query ) {
			int result = 0;
			Statement stmt = null;

			try {
				stmt = connection.createStatement();
				
				logger.info( query );
				
				result = stmt.executeUpdate( query );

				logger.info( result + " records changed" );
				
				return result;
				
			} catch (SQLException e) {
				logger.error( "Error!", e );
				throw new RuntimeException( e );			
			}
		}
		
		void close() {
			try {
				connection.close();
			} catch ( SQLException e ) {
				logger.error( "Error!", e );
			}
		}
	}
}
