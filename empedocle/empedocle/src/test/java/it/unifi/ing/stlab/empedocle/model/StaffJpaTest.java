package it.unifi.ing.stlab.empedocle.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.StaffFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;

import org.junit.Test;

public class StaffJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		User user = UserFactory.createUser();
		user.setUserid( "userid" );
		user.setPassword( "password" );
		
		Staff staff = StaffFactory.createStaff();
		staff.setUser( user );
		staff.setNumber( "12345" );
		
		entityManager.persist( staff );

		uuid = staff.getUuid();
	}
	
	@Test
	public void testStaff() {
		Staff staff = (Staff)
			entityManager
				.createQuery( 
					"select p " +
					" from Staff p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( staff );
		
		assertEquals( "userid", staff.getUser().getUserid());
		assertEquals( "password", staff.getUser().getPassword() );
	}
	
}
