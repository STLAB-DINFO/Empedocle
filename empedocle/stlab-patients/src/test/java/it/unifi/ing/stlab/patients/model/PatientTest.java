package it.unifi.ing.stlab.patients.model;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.patients.factory.PatientFactory;

import org.junit.Test;

public class PatientTest {

	@Test
	public void testSameAs1() {
		Patient patient1 = PatientFactory.createPatient();
		PatientIdentifier identifier = PatientFactory.createPatientIdentifier();
		identifier.setCode( "id" );
		patient1.setIdentifier( identifier );

		Patient patient2 = PatientFactory.createPatient();

		assertEquals( false, patient1.sameAs( patient2 ) );
		assertEquals( false, patient2.sameAs( patient1 ) );
	}
	
	@Test
	public void testSameAs2() {
		Patient patient1 = PatientFactory.createPatient();		
		Patient patient2 = PatientFactory.createPatient();

		assertEquals( true, patient1.sameAs( patient2 ) );
		assertEquals( true, patient2.sameAs( patient1 ) );
	}
	
	@Test
	public void testSameAs3() {
		Patient patient1 = PatientFactory.createPatient();
		PatientIdentifier identifier = PatientFactory.createPatientIdentifier();
		identifier.setCode( "id" );
		patient1.setIdentifier( identifier );

		Patient patient2 = PatientFactory.createPatient();
		patient2.setIdentifier( identifier );

		assertEquals( true, patient1.sameAs( patient2 ) );
		assertEquals( true, patient2.sameAs( patient1 ) );
	}
	
	@Test
	public void testSameAs4() {
		Patient patient1 = PatientFactory.createPatient();
		PatientIdentifier identifier1 = PatientFactory.createPatientIdentifier();
		identifier1.setCode( "id1" );
		patient1.setIdentifier( identifier1 );

		Patient patient2 = PatientFactory.createPatient();
		PatientIdentifier identifier2 = PatientFactory.createPatientIdentifier();
		identifier2.setCode( "id2" );
		patient2.setIdentifier( identifier2 );

		assertEquals( false, patient1.sameAs( patient2 ) );
		assertEquals( false, patient2.sameAs( patient1 ) );
	}	

}
