/*
package it.unifi.ing.stlab.empedocle.services.mpi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergeInformation;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergePatient;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.ObjectFactory;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.PatientIdentification;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.PatientIdentifiers;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.UpdatePersonInformation;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.actions.PatientMergeAction;
import it.unifi.ing.stlab.patients.model.actions.PatientModifyAction;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.factory.UserFactory;

public class MasterPatientIndexServiceTest extends PersistenceTest {
	
	private MasterPatientIndexServiceImpl mpiService;
	
	protected PatientDao patientDao;
	protected UserDao userDao;
	protected Logger logger;
	protected UserTransaction utx;
	
	private UpdatePersonInformation updatePersonInformation;
	private MergePatient mergePatient;
	
	@Override 
	protected void insertData() throws Exception {
		mpiService = new MasterPatientIndexServiceImpl();
		
		patientDao = mock( PatientDao.class );
		userDao = mock( UserDao.class );
		logger = Logger.getLogger( MasterPatientIndexServiceImpl.class );
		utx = mock( UserTransaction.class );
		
		FieldUtils.assignField( mpiService, "entityManager", entityManager);
		FieldUtils.assignField( mpiService, "patientDao", patientDao );
		FieldUtils.assignField( mpiService, "userDao", userDao );
		FieldUtils.assignField( mpiService, "logger", logger );
		FieldUtils.assignField( mpiService, "utx", utx );
		
		updatePersonInformation = initUpdatePersonInformation();
		mergePatient = initMergePatient();

		when( userDao.findByUsername( "administrator" )).thenReturn( UserFactory.createUser() );
		
		doNothing().when( utx ).begin();
		doNothing().when( utx ).commit();
//		doNothing().when( logger ).info( anyString() );
	}

	@Test
	public void testUpdate_patientNotFound() {
		when( patientDao.findByIdentifier( updatePersonInformation.getPatientIdentification()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.update( updatePersonInformation );
	}
	
	@Test
	public void testUpdate_patientFound() {
		Patient patient = PatientFactory.createPatient();
		
		assertNull( patient.getDestination() );

		when( patientDao.findByIdentifier( updatePersonInformation.getPatientIdentification()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( patient );
		mpiService.update( updatePersonInformation );
		
		// a new PatientModifyAction is created
		assertNotNull( patient.getDestination() );
		assertTrue( patient.getDestination() instanceof PatientModifyAction);
		
		// the PatientModifyAction's source is patient
		assertEquals( patient, ((PatientModifyAction) patient.getDestination()).getSource() );
		
		// the PatientModifyAction's target is a new patient with the data collected in updatePersonInformation
		assertNotEquals( patient, ((PatientModifyAction) patient.getDestination()).getTarget() );
		assertEquals( updatePersonInformation.getPatientIdentification().getPatientIdentifiers().getIdAce(),
				((PatientModifyAction) patient.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( updatePersonInformation.getPatientIdentification().getCognome(),
				((PatientModifyAction) patient.getDestination()).getTarget().getSurname() );
		assertEquals( updatePersonInformation.getPatientIdentification().getNome(),
				((PatientModifyAction) patient.getDestination()).getTarget().getName() );
	}
	
	@Test
	public void testMerge_masterAndSlaveNotFound() {
		when( patientDao.findByIdentifier(
				mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce() ) )
						.thenReturn( null );
		mpiService.merge( mergePatient );
	}
	
	@Test
	public void testMerge_masterFoundSlaveNotFound() {
		Patient master = PatientFactory.createPatient();
		
		assertNull( master.getDestination() );

		when( patientDao.findByIdentifier( mergePatient.getPatientIdentification()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( master );
		when( patientDao.findByIdentifier( mergePatient.getMergeInformation()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.merge( mergePatient );
		
		// a new PatientModifyAction is created
		assertNotNull( master.getDestination() );
		assertTrue( master.getDestination() instanceof PatientModifyAction);
		
		// the PatientModifyAction's source is the master patient
		assertEquals( master, ((PatientModifyAction) master.getDestination()).getSource() );
		
		// the PatientModifyAction's target is a new patient with the data collected in mergePatient
		assertNotEquals( master, ((PatientModifyAction) master.getDestination()).getTarget() );
		assertEquals( mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce(),
				((PatientModifyAction) master.getDestination()).getTarget().getIdentifier().getCode());
		assertEquals( mergePatient.getPatientIdentification().getCognome(),
				((PatientModifyAction) master.getDestination()).getTarget().getSurname() );
		assertEquals( mergePatient.getPatientIdentification().getNome(),
				((PatientModifyAction) master.getDestination()).getTarget().getName() );
		assertNull(((PatientModifyAction) master.getDestination()).getTarget().getDestination());
	}
	
	@Test
	public void testMerge_masterNotFoundSlaveFound() {
		Patient slave = PatientFactory.createPatient();
		
		assertNull( slave.getDestination() );

		when( patientDao.findByIdentifier( mergePatient.getPatientIdentification()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( null );
		when( patientDao.findByIdentifier( mergePatient.getMergeInformation()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergePatient );
		
		// a new PatientMergeAction is created to merge the slave patient and a new master patient generated ex-novo
		assertNotNull( slave.getDestination() );
		assertTrue( slave.getDestination() instanceof PatientMergeAction);
		
		// the PatientMergeAction's source2 is the slave patient
		assertEquals( slave, ((PatientMergeAction) slave.getDestination()).getSource2() );
		
		// the PatientMergeAction's source1 is the new master patient generated ex-novo starting from 
		// the new data collected in mergePatient
		assertEquals( mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce(), 
				((PatientMergeAction) slave.getDestination()).getSource1().getIdentifier().getCode() );
		assertEquals( mergePatient.getPatientIdentification().getNome(), 
				((PatientMergeAction) slave.getDestination()).getSource1().getName() );
		assertEquals( mergePatient.getPatientIdentification().getCognome(), 
				((PatientMergeAction) slave.getDestination()).getSource1().getSurname() );
		
		// the PatientMergeAction's target is a copy of the master patient
		assertNotEquals( ((PatientMergeAction) slave.getDestination()).getSource1(), 
				((PatientMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergePatient.getPatientIdentification().getNome(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergePatient.getPatientIdentification().getCognome(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	@Test
	public void testMerge_masterFoundSlaveFound() {
		Patient master = PatientFactory.createPatient();
		Patient slave = PatientFactory.createPatient();
		
		assertNull( master.getDestination() );
		assertNull( slave.getDestination() );

		when( patientDao.findByIdentifier( mergePatient.getPatientIdentification()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( master );
		when( patientDao.findByIdentifier( mergePatient.getMergeInformation()
				.getPatientIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergePatient );
		
		assertNotNull( master.getDestination() );
		assertNotNull( slave.getDestination() );
		
		// a new PatientModifyAction is created to update the master patient
		assertTrue( master.getDestination() instanceof PatientModifyAction );
		assertEquals( master, ((PatientModifyAction) master.getDestination()).getSource() );
		assertEquals( mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce(), 
				((PatientModifyAction) master.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergePatient.getPatientIdentification().getNome(), 
				((PatientModifyAction) master.getDestination()).getTarget().getName() );		
		assertEquals( mergePatient.getPatientIdentification().getCognome(), 
				((PatientModifyAction) master.getDestination()).getTarget().getSurname() );				

		// a new PatientMergeAction is created to merge the updated version of master patient and the slave patient
		assertTrue( slave.getDestination() instanceof PatientMergeAction );
		// the PatientMergeAction's source1 is the copy of the master patient after the PatientModifyAction
		assertEquals( ((PatientModifyAction) master.getDestination()).getTarget(), 
				((PatientMergeAction) slave.getDestination()).getSource1() );
		// the PatientMergeAction's source2 is the slave patient
		assertEquals( slave, ((PatientMergeAction) slave.getDestination()).getSource2() );
		// the PatientMergeAction's target is a copy of the master patient
		assertNotEquals( ((PatientMergeAction) slave.getDestination()).getSource1(), 
				((PatientMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergePatient.getPatientIdentification().getPatientIdentifiers().getIdAce(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergePatient.getPatientIdentification().getNome(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergePatient.getPatientIdentification().getCognome(), 
				((PatientMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	private UpdatePersonInformation initUpdatePersonInformation() {
		ObjectFactory of = new ObjectFactory();
		UpdatePersonInformation upi = of.createUpdatePersonInformation();
		
		// create patient information with id ACE and some personal data
		PatientIdentification identification = of.createPatientIdentification();
		PatientIdentifiers identifiers = of.createPatientIdentifiers();
		identifiers.setIdAce( "p001" );
		identification.setPatientIdentifiers( identifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );

		// add patient information to upi
		upi.setPatientIdentification( identification );
		
		return upi;
	}
	
	private MergePatient initMergePatient() {
		ObjectFactory of = new ObjectFactory();
		MergePatient mp = of.createMergePatient();
		
		// create master patient information with id ACE and some personal data
		PatientIdentification identification = of.createPatientIdentification();
		PatientIdentifiers masterIdentifiers = of.createPatientIdentifiers();
		masterIdentifiers.setIdAce( "master" );
		identification.setPatientIdentifiers( masterIdentifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );
		
		// create slave patient information with id ACE
		MergeInformation mergeInfo = of.createMergeInformation();
		PatientIdentifiers slaveIdentifiers = of.createPatientIdentifiers();
		slaveIdentifiers.setIdAce( "slave" );
		mergeInfo.setPatientIdentifiers( slaveIdentifiers );
		
		// add master information to mp
		mp.setPatientIdentification( identification );
		// add slave information to mp
		mp.setMergeInformation( mergeInfo );
		
		return mp;
	}

}
*/
