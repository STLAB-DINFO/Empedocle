/*
package it.unifi.ing.stlab.empedocle.services.appointments;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
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

import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.health.ServiceDao;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Place;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.CheckSumFactory;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.manager.PatientManager;
import it.unifi.ing.stlab.patients.model.Address;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.patients.model.Sex;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.time.Time;

@Stateless
@WebService(serviceName = "appointmentService", 
	endpointInterface = "it.unifi.ing.stlab.empedocle.services.appointments.AppointmentService", 
	targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-service")
@TransactionManagement( TransactionManagementType.BEAN )
@HandlerChain( file = "/soap-handlers.xml" )
public class AppointmentServiceBean implements AppointmentService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private PatientDao patientDao;
	
	@EJB
	private ServiceDao serviceDao;
	
	@EJB
	private ExaminationDao examinationDao;		
	
	@EJB
	private AgendaDao agendaDao;
	
	@EJB
	private UserDao userDao;
	
	@Inject
	private Logger logger;
	
	@Resource
	private UserTransaction utx;
	
	private Patient currentPatient;
	
	private static final int RETRY_LIMIT = 20;
	private int retryCount = 0;
	
	public void handleAppointment(String applicationSender, Date timestamp,
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Patient patientDetails, 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails) {
		
		if( retryCount == 0 ) {
			logger.info( "handleAppointment started [" + this.hashCode() +"]" );
		}
		
		try {
			utx.begin();

			handlePatientDetails( patientDetails );
			handleAppointmentDetails( appointmentDetails, patientDetails.getIdentifiers().getIdAce() );

			utx.commit();
			
		} catch ( RollbackException rbe ) {	
			retryCount++;
			if( retryCount > RETRY_LIMIT )
				throw new RuntimeException( "retry limit exceeded, message not handled" );
			
			logger.info( "retry handleAppointment after a transaction rollback, " +
							"retry "+ retryCount + " of " + RETRY_LIMIT + " [" + this.hashCode() +"]");
			
			this.handleAppointment(applicationSender, timestamp, patientDetails, appointmentDetails);
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
				
			} catch (Exception ute) {
				logger.error( ute );
				
			}
			
		} finally {
			logger.info( "handleAppointment ended [" + this.hashCode() +"]" );
			retryCount = 0;
		}
	}
	
	private void handlePatientDetails( 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Patient patientDetails ) {
		
		PatientManager patientManager = new PatientManager();
		Patient patient = patientDao.findByIdentifier( patientDetails.getIdentifiers().getIdAce() );

		if ( patient == null ) {
			patient = patientManager.createPatient( userDao.findByUsername( "administrator" ), new Time( new Date() ));
			updatePatient( patient, patientDetails );
			
			entityManager.persist( patient );
			
			currentPatient = patient;

		} else {
			Patient copy = patientManager.modify( userDao.findByUsername( "administrator" ),
					new Time( new Date() ), patient );
			updatePatient( copy, patientDetails );
			Patient purged = patientManager.purge( copy );
			
			if ( purged != null ) {
				entityManager.persist( purged );
				currentPatient = purged;
				
				// adesso i riferimenti agli appointment rimangono sempre sullo stesso paziente
				// updateAppointmentsReferences();
			} else {
				currentPatient = patient;
			}
		}
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}	
	
	@SuppressWarnings("unused")
	private void updateAppointmentsReferences() {
		List<Appointment> appointments = findAppointmentsByPatients( currentPatient.listBefore() );		
		
		for( Appointment a : appointments ) {
			a.setPatient( currentPatient );
		}
	}	
	
	@SuppressWarnings("unchecked")
	private List<Appointment> findAppointmentsByPatients( Set<Patient> patients ) {
		return ( List<Appointment> )entityManager.createQuery( 
				"select a" +
				" from Appointment a" +
				" where a.patient in :patients " )
				.setParameter( "patients", patients )
				.getResultList();	
	}
	
	private void handleAppointmentDetails(
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails, 
			String patientIdentifier) {	
				
		switch ( appointmentDetails.getStatus() ) {
		case BOOKED:
			applyBookedStrategy( appointmentDetails, patientIdentifier );
			break;
		case ACCEPTED:
			applyAcceptedStrategy( appointmentDetails, patientIdentifier );
			break;
		case CANCELED:
			applyCanceledStrategy( appointmentDetails );
			break;
		}
	}	
	
	private void updatePatient( Patient patient, 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Patient patientDetails) {
		
		PatientIdentifier identifier = obtainPatientIdentifier( patientDetails.getIdentifiers().getIdAce() );
		patient.setIdentifier( identifier );
		
		patient.setTaxCode( patientDetails.getIdentifiers().getTaxCode() );
		patient.setSsnCode( patientDetails.getIdentifiers().getSsnCode() );
		
		patient.setName( patientDetails.getName() );
		patient.setSurname( patientDetails.getSurname() );
		patient.setSex( convertSex( patientDetails.getSex() ));
		patient.setBirthDate( patientDetails.getBirthDate() );
		patient.setBirthPlace( buildPlace( patientDetails.getBirthPlace() ));

		if ( patientDetails.getResidence() != null ) {
			patient.setResidence( new Address() );
			patient.getResidence().setPlace( 
					buildPlace( patientDetails.getResidence() ));
		}
		
		if ( patientDetails.getDomicile() != null ) {
			patient.setDomicile( new Address() );
			patient.getDomicile().setPlace( 
					buildPlace( patientDetails.getDomicile() ));
		}

		patient.setRegion( patientDetails.getRegion() );
		
		if ( patientDetails.getPhones() != null ) {
			patient.setHomePhone( patientDetails.getPhones().getHomePhone() );
			patient.setWorkPhone( patientDetails.getPhones().getWorkPhone() );
		}

		patient.setNationality( patientDetails.getNationality() );
		patient.setAsl( patientDetails.getAsl() );		
	}

	private void applyBookedStrategy( 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails, 
			String patientIdentifier ) {
		
		Examination examination = examinationDao.findByAppointmentCodes(
				appointmentDetails.getBookingCode(), null );
		
		if ( examination == null ) {
			examination = ExaminationFactory.createExamination();
			updateExamination( examination, appointmentDetails );
		} else {
			// FIXME in casi di questo tipo, sarebbe meglio rimettere il messaggio nella coda
			if ( examination.getStatus().equals( ExaminationStatus.RUNNING ))
				return;
			
			if ( examination.getAppointment().getStatus().equals( AppointmentStatus.ACCEPTED )) {
				examination.setLastUpdate( new Date() );
			} else {
				updateExamination( examination, appointmentDetails );
			}
		}
		
		entityManager.persist( examination );
	}

	private void applyAcceptedStrategy( 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails, 
			String patientIdentifier ) {
		
		// appointment booked and accepted
		Examination examination = examinationDao.findByAppointmentCodes(
				appointmentDetails.getBookingCode(),
				appointmentDetails.getAcceptanceCode() );
		
		if ( examination == null ) {
			// appointment booked but not accepted yet
			examination = examinationDao.findByAppointmentCodes(
				appointmentDetails.getBookingCode(), null );
		}

		if ( examination == null ) {
			examination = ExaminationFactory.createExamination();
		} 
		
		if ( examination.getStatus() != null
				&& examination.getStatus().equals( ExaminationStatus.RUNNING ))
			return;
		
		updateExamination( examination, appointmentDetails );
		entityManager.persist( examination );
	}

	private void applyCanceledStrategy( 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails ) {
		
		Examination examination = examinationDao.findByAppointmentCodes(
				appointmentDetails.getBookingCode(),
				appointmentDetails.getAcceptanceCode() );
		
		if( examination != null ) {
			if ( examination.getStatus().equals( ExaminationStatus.TODO )) {
				entityManager.remove( examination.getAppointment() );
				entityManager.remove( examination ); 
			} else {
				if ( examination.getStatus().equals( ExaminationStatus.RUNNING ))
					return;
				
				examination.getAppointment().setStatus( AppointmentStatus.CANCELED );
				examination.setLastUpdate( new Date() );
				entityManager.persist( examination );
			}		
		} else {
			throw new RuntimeException( "Error: cannot find the examination you wish to remove" );
		}
	}
	
	private void updateExamination( Examination examination,
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails ) {

		if ( examination.getStatus() == null ) {
			examination.setStatus( ExaminationStatus.TODO );
		} 
		
		examination.setLastUpdate( new Date() );
		
		Appointment appointment = examination.getAppointment();
		
		if( appointment == null ) {
			appointment = AppointmentFactory.createAppointment();
			examination.setAppointment( appointment );
		}
		
		updateAppointment( appointment, appointmentDetails );
	}
	
	private void updateAppointment(
			Appointment appointment,
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment appointmentDetails ) {

		appointment.setDate( appointmentDetails.getDate() );
		appointment.setStatus( convertAppointmentStatus( appointmentDetails.getStatus()) );		
		appointment.setNumber( appointmentDetails.getNumber() );
		appointment.setBookingCode( appointmentDetails.getBookingCode() );
		appointment.setAcceptanceCode( appointmentDetails.getAcceptanceCode() );
		appointment.setPatient( currentPatient ); 
		appointment.clearServices();
		
		for (it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Service serviceDetails : appointmentDetails
				.getServices().getService()) {
			
			String code = serviceDetails.getCode();
			String description = serviceDetails.getDescription();
			String agendaCode = appointmentDetails.getAgenda();
			
			Service service = serviceDao.find( code, description, agendaCode );
						
			if( service == null ) {
				service = ServiceFactory.createService();
				service.setCode( code );
				service.setDescription( description );
				
				Agenda agenda = agendaDao.findByCode( agendaCode );
				
				if( agenda == null ) {
					agenda = AgendaFactory.createAgenda();
					agenda.setCode( agendaCode );
				}
					
				service.setAgenda( agenda );
				service.setInternalCode( CheckSumFactory.createCheckSum( code, description, agendaCode ));
			}
			
			appointment.setAgenda( service.getAgenda() );
			appointment.addService( service );
		}
	}

	private Sex convertSex(
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Sex s) {
		if ( s == null ) return null;
		if ( s.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Sex.M ))
			return Sex.M;
		if ( s.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Sex.F ))
			return Sex.F;
		if ( s.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Sex.N_D ))
			return Sex.N;
		return null;
	}
	
	private AppointmentStatus convertAppointmentStatus( 
			it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.AppointmentStatus status ) {
		if ( status == null ) return null;
		if ( status.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.AppointmentStatus.BOOKED ))
			return AppointmentStatus.BOOKED;
		if ( status.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.AppointmentStatus.ACCEPTED )) 
			return AppointmentStatus.ACCEPTED;
		if ( status.equals( it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.AppointmentStatus.CANCELED )) 
			return AppointmentStatus.CANCELED;
		return null;
	}	
	
	private String buildPlace( Place place ) {
		StringBuilder placeBuilder = new StringBuilder();
		
		if (place != null) {
			if (place.getAddress() != null ) {
				placeBuilder.append( place.getAddress() );
				placeBuilder.append(" ");
			}
			
			if (place.getCity() != null ) {
				placeBuilder.append( place.getCity() );
				placeBuilder.append(" ");
			}
			
			if (place.getDistrict() != null ) {
				placeBuilder.append( "(" + place.getDistrict() + ")" );
				placeBuilder.append(" ");
			}			
			
			if (place.getCap() != null ) {
				placeBuilder.append( place.getCap() );
			}
		}
		return placeBuilder.toString().trim();
	}
	
	private PatientIdentifier obtainPatientIdentifier(String code){
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
