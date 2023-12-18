package it.unifi.ing.stlab.patients.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.actions.PatientMergeAction;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PatientManagerTest {

	protected PatientManager manager;
	protected Patient patient1;
	protected Patient patient2;
	
	@Before
	public void setUp() {
		manager = new PatientManager();
		
		patient1 = PatientFactory.createPatient();
		patient1.setName( "Prova1" );
		
		patient2 = PatientFactory.createPatient();
		patient2.setName( "Prova2" );
	}
	
	@Test
	public void testMergePatients() {
		Patient merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												patient1, patient2 ); 
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( patient1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains( patient1 ) );
		assertTrue( merged.listBefore().contains( patient2 ) );
		assertEquals( PatientMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( patient1, ((PatientMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( patient2, ((PatientMergeAction)merged.getOrigin()).getSource2() );
	}
	
	@Test
	public void testMergePatients_Null() {
		Patient merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												null, patient2 ); 
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( patient1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains( patient1 ) );
		assertTrue( merged.listBefore().contains( patient2 ) );
		assertEquals( PatientMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( patient1, ((PatientMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( patient2, ((PatientMergeAction)merged.getOrigin()).getSource2() );
	}
	
	
}
