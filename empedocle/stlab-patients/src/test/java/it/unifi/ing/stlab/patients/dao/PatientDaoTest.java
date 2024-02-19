package it.unifi.ing.stlab.patients.dao;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.manager.PatientManager;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class PatientDaoTest extends PersistenceTest {

	protected PatientDao dao;
	
	protected Patient p1, p2, p3;
	protected User author;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		dao = new PatientDaoBean();
		FieldUtils.assignField( dao, "entityManager", entityManager );
	}
	
	@Override
	public void insertData() {
		p1 = PatientFactory.createPatient();
		p1.setIdentifier( PatientFactory.createPatientIdentifier() );
		p1.getIdentifier().setCode( "1234" );
		p1.setName( "name" );
		p1.setSurname( "surname" );
		entityManager.persist( p1 );
		
		p2 = PatientFactory.createPatient();
		p2.setIdentifier( PatientFactory.createPatientIdentifier() );
		p2.getIdentifier().setCode( "5678" );
		entityManager.persist( p2 );
		
		author = UserFactory.createUser();
		entityManager.persist( author );
		
		PatientManager manager = new PatientManager();
		p3 = manager.merge( author, new Time( new Date() ), p1, p2 );
		entityManager.persist( p3 );
	}
	
	@Test
	public void testFindLastVersionById() {
		Patient result1 = dao.findLastVersionById( p1.getId() );
		assertEquals( p3, result1 );
		
		Patient result2 = dao.findLastVersionById( p2.getId() );
		assertEquals( p3, result2 );

		Patient result3 = dao.findLastVersionById( p3.getId() );
		assertEquals( p3, result3 );
	}
	
	@Test
	public void testFindByIdentifier() {
		Patient result = dao.findByIdentifier( "1234" );
		
		assertEquals( p3, result );
	}
	
	@Test
	public void testFindForMerge() {
		Patient p4 = PatientFactory.createPatient();
		p4.setName( "name" );
		p4.setSurname( "surname" );
		entityManager.persist( p4 );
		
		List<Patient> result = dao.findForMerge(p3.getName(), p3.getSurname(), p3.getId());
		
		assertEquals( 1, result.size() );
		assertEquals( p4, result.get(0) );
	}
	
	@Test
	public void testMergePatients() {
		Patient p4 = PatientFactory.createPatient();
		entityManager.persist( p4 );
		
		Patient result = dao.mergePatients( p4.getId(), p3.getId(), author );
		
		assertEquals( "1234", result.getIdentifier().getCode() );
		assertEquals( "name", result.getName() );
		assertEquals( "surname", result.getSurname() );
	}

	@Test
	public void testFindByName() {
		List<Patient> result = dao.findByName( "name", "surname" );
		
		assertEquals( 1, result.size() );
		assertEquals( p3, result.get( 0 ) );
	}
	
}
