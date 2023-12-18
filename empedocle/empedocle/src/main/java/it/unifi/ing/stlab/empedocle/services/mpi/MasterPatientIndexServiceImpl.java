/*
package it.unifi.ing.stlab.empedocle.services.mpi;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.Indirizzo;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergeInformation;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergePatient;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.PatientIdentification;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.PatientIdentifiers;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.Sesso;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.Telefono;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.UpdatePersonInformation;
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

//XXX verificare se questa implementazione permette situazioni in cui un paziente che non è la versione finale (target == null)
//può essere updated o merged con un altro. Stessa cosa per l'altro servizio.
@WebService( serviceName = "mpiService", 
	endpointInterface = "it.unifi.ing.stlab.empedocle.services.mpi.MasterPatientIndexService", 
	targetNamespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/mpiService" )
@HandlerChain( file = "/soap-handlers.xml" )
public class MasterPatientIndexServiceImpl implements MasterPatientIndexService {

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private PatientDao patientDao;

	@EJB
	private UserDao userDao;

	@Resource
	private UserTransaction utx;

	@Inject
	private Logger logger;
	
	private static final int RETRY_LIMIT = 20;
	private int retryCount = 0;
	
	@Override
	public void update( UpdatePersonInformation body ) {
		
		if( retryCount == 0 ) {
			logger.info( "update started [" + this.hashCode() + "]" );
		}

		try {
			utx.begin();
			
			PatientIdentification patientInfo = body.getPatientIdentification();
			Patient patient = patientDao.findByIdentifier(
					patientInfo.getPatientIdentifiers().getIdAce() );
	
			if ( patient == null ) {
				throw new RuntimeException( "Error: cannot find the patient you wish to update" );
			}
			
			PatientManager patientManager = new PatientManager();
			User author = userDao.findByUsername( "administrator" );
			Time time = new Time( new Date() );
	
			Patient copy = patientManager.modify( author, time, patient );
			update( copy, patientInfo );
			
			Patient purged = patientManager.purge( copy );
			if ( purged != null ) {
				entityManager.persist( purged );
				
				//XXX per ora i riferimenti agli appointment rimangono sempre sullo stesso paziente
				// updateAppointmentsReferences();
			}
					
			utx.commit();
		} catch ( RollbackException rbe ) {	
			retryCount++;
			if( retryCount > RETRY_LIMIT )
				throw new RuntimeException( "retry limit exceeded, message not handled" );
			
			logger.info( "retry update after a transaction rollback, " +
							"retry "+ retryCount + " of " + RETRY_LIMIT + " [" + this.hashCode() +"]");
			
			this.update( body );
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
				
			} catch (Exception ute) {
				logger.error( ute );
				
			}
			
		} finally {
			logger.info( "update ended [" + this.hashCode() +"]" );
			retryCount = 0;
		}
	}

	@Override
	public void merge( MergePatient body ) {
		
		if( retryCount == 0 ) {
			logger.info( "merge started [" + this.hashCode() + "]" );
		}

		try {
			utx.begin();

			PatientIdentification masterInfo = body.getPatientIdentification();
			MergeInformation slaveInfo = body.getMergeInformation();

			Patient master = patientDao
					.findByIdentifier( masterInfo.getPatientIdentifiers().getIdAce() );
			Patient slave = patientDao
					.findByIdentifier( slaveInfo.getPatientIdentifiers().getIdAce() );

			PatientManager patientManager = new PatientManager();
			User author = userDao.findByUsername( "administrator" );
			Time time = new Time( new Date() );
			
			if ( master == null ) {
				// master not found
				if ( slave == null ) {
					// master and slave not found: patient not managed by empedocle
					throw new RuntimeException( "Error: cannot find the patients you wish to merge" );
				} else {
					// slave found, master not found

					// 1. a new patient with role master is created
					master = patientManager.createPatient( author, time );
					update( master, masterInfo );
					entityManager.persist( master );

					// 2. master and slave are merged
					Patient result = patientManager.merge( author, time, master, slave );
					entityManager.persist( result );
				}
			} else {
				// master found
				
				// 1. master is updated
				Patient copy = patientManager.modify( author, time, master );
				update( copy, masterInfo );
				
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
				}
			}

			utx.commit();
		} catch ( RollbackException rbe ) {	
			retryCount++;
			if( retryCount > RETRY_LIMIT )
				throw new RuntimeException( "retry limit exceeded, message not handled" );
			
			logger.info( "retry merge after a transaction rollback, " +
							"retry "+ retryCount + " of " + RETRY_LIMIT + " [" + this.hashCode() +"]");
			
			this.merge( body );
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
				
			} catch (Exception ute) {
				logger.error( ute );
				
			}
			
		} finally {
			logger.info( "merge ended [" + this.hashCode() +"]" );
			retryCount = 0;
		}
	}

	private void update( Patient patient, PatientIdentification infos ) {
		
		PatientIdentifiers ids = infos.getPatientIdentifiers();
		PatientIdentifier identifier = retrievePatientIdentifier( ids.getIdAce() );
		patient.setIdentifier( identifier );
		
		patient.setTaxCode( ids.getCodiceFiscale() );
		patient.setSsnCode( ids.getTesseraSanitaria() != null ? 
				ids.getTesseraSanitaria().getNumeroIdentificativoTessera() : null );
		
		patient.setName( infos.getNome() );
		patient.setSurname( infos.getCognome() );
		patient.setSex( convertSex( infos.getSesso() ));
		patient.setBirthDate( DateUtils.asDate( infos.getDataNascita() ) );
		patient.setBirthPlace( infos.getLuogoNascita() != null ? 
				infos.getLuogoNascita().getDescrizione() : null );

		if ( infos.getResidenza() != null ) {
			patient.setResidence( new Address() );
			patient.getResidence().setPlace( 
					buildPlace( infos.getResidenza() ));
		} else {
			patient.setResidence( null );
		}
		
		if ( infos.getDomicilio() != null ) {
			patient.setDomicile( new Address() );
			patient.getDomicile().setPlace( 
					buildPlace( infos.getDomicilio() ));
		} else {
			patient.setDomicile( null );
		}
		
		if ( infos.getRecapiti() != null ) {
			List<Telefono> phones = infos.getRecapiti().getTelefono();
			
			StringBuilder hpb = new StringBuilder();
			StringBuilder wpb = new StringBuilder();
			for( Telefono t : phones ) {
				switch ( t.getType() ) {
					case "residenza" :
					case "domicilio/altro" :
						hpb.append( t.getNumero() );
						hpb.append( " - ");
						break;
					case "lavoro" :
						wpb.append( t.getNumero() );
						wpb.append( " - ");
						break;
				}
			}
			String homePhones = hpb.toString();
			String workPhones = wpb.toString();

			if( homePhones != null && !homePhones.isEmpty() ) 
				patient.setHomePhone( homePhones.substring( 0, homePhones.length() - 3 ) );
			
			if( workPhones != null && !workPhones.isEmpty() ) 
				patient.setWorkPhone( workPhones.substring( 0, workPhones.length() - 3 ) );
		} else {
			patient.setHomePhone( null );
			patient.setWorkPhone( null );
		}

		patient.setNationality( infos.getCittadinanza() != null ? 
				infos.getCittadinanza().getDescrizione() : null );
	}
	
	private String buildPlace( Indirizzo place ) {
		StringBuilder placeBuilder = new StringBuilder();

		if ( place != null ) {
			if ( place.getIndirizzo() != null ) {
				placeBuilder.append( place.getIndirizzo() );
				placeBuilder.append( " " );
			}

			if ( place.getComune() != null ) {
				placeBuilder.append( place.getComune().getDescrizione() );
				placeBuilder.append( " " );
			}

			if ( place.getProvincia() != null ) {
				placeBuilder.append( "(" + place.getProvincia().getDescrizione() + ")" );
				placeBuilder.append( " " );
			}

			if ( place.getCap() != null ) {
				placeBuilder.append( place.getCap() );
			}
		}
		return placeBuilder.toString().trim();
	}

	private Sex convertSex( Sesso s ) {
		if ( s == null )
			return null;
		if ( s.equals( Sesso.M ) )
			return Sex.M;
		if ( s.equals( Sesso.F ) )
			return Sex.F;
		return null;
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

}
*/
