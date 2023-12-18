package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class ServiceJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		Service service = ServiceFactory.createService();
		service.setCode( "code" );
		service.setDescription( "description" );
		
		entityManager.persist( service );

		uuid = service.getUuid();
	}
	
	@Test
	public void testService() {
		Service service = (Service)
			entityManager
				.createQuery( 
					"select p " +
					" from Service p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( service );
		
		assertEquals( "code", service.getCode());
		assertEquals( "description", service.getDescription());
	}
	
}
