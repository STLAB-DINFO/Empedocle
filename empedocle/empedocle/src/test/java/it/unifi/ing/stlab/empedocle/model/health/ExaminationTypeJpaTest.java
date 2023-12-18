package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class ExaminationTypeJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		ExaminationType type = ExaminationTypeFactory.createExaminationType();
		type.setName( "name" );
		type.setDescription( "description" );
		type.setTimeToLive( new Integer( 10 ));
		
		entityManager.persist( type );

		uuid = type.getUuid();
	}
	
	@Test 
	public void testExaminationType() {
		ExaminationType type = (ExaminationType)
			entityManager
				.createQuery( 
					"select p " +
					" from ExaminationType p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( type );
		
		assertEquals( "name", type.getName());
		assertEquals( "description", type.getDescription());
		assertEquals( new Integer( 10 ), type.getTimeToLive() );
	}
	
}
