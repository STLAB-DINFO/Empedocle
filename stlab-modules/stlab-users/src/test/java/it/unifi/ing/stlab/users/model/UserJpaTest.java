package it.unifi.ing.stlab.users.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;

import org.junit.Test;

public class UserJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		Role role = RoleFactory.createRole();
		entityManager.persist( role );
		
		Qualification qualification = QualificationFactory.createQualification();
		entityManager.persist( qualification );
		  
		User user = UserFactory.createUser();
		user.setUserid( "userid" );
		user.setPassword( "password" );
		user.setName( "name" );
		user.setSurname( "surname" );
		user.setMail( "mail" );
		user.addRole( role );
		user.addQualification( qualification );
		
		entityManager.persist( user );

		uuid = user.getUuid();
	}
	
	@Test
	public void testUser() {
		User user = (User)
			entityManager
				.createQuery( 
					"select p " +
					" from User p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( user );
		
		assertEquals( "userid", user.getUserid());
		assertEquals( "password", user.getPassword());
		assertEquals( "name", user.getName());
		assertEquals( "surname", user.getSurname());
		assertEquals( "mail", user.getMail());
		assertEquals( 1, user.listRoles().size() );
		assertEquals( 1, user.listQualifications().size() );
	}
	
}
