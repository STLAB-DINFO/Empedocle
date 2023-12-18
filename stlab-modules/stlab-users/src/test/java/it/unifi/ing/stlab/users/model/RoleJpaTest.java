package it.unifi.ing.stlab.users.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class RoleJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		Role role = RoleFactory.createRole();
		role.setName( "admin" );

		entityManager.persist( role );

		uuid = role.getUuid();
	}
	
	@Test
	public void testRole() {
		Role role = (Role)
			entityManager
				.createQuery( 
					"select r " +
					" from Role r " +
					" where r.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( role );
		assertEquals( "admin", role.getName() );
	}
	
}
