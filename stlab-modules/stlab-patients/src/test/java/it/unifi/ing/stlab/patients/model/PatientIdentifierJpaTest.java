package it.unifi.ing.stlab.patients.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class PatientIdentifierJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		PatientIdentifier identifier = PatientFactory.createPatientIdentifier();
		identifier.setCode( "IDENTIFIER001" );
		
		entityManager.persist( identifier );

		uuid = identifier.getUuid();
	}
	
	@Test
	public void testPatient() {
		PatientIdentifier identifier = (PatientIdentifier)
			entityManager
				.createQuery( 
					"select p " +
					" from PatientIdentifier p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( identifier );
		assertEquals( "IDENTIFIER001", identifier.getCode());

	}
	
}
