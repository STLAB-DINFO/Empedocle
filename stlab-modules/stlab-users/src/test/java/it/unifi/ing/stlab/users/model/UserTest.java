package it.unifi.ing.stlab.users.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	protected User user;
	
	@Before
	public void setUp() {
		user = UserFactory.createUser();
	}
	
	
	//
	// Roles
	//
	
	@Test
	public void testListRoles() {
		assertEquals( 0, user.listRoles().size() );
	}
	
	@Test
	public void testAddRole() {
		Role role = RoleFactory.createRole();
		user.addRole( role );

		assertEquals( 1, user.listRoles().size() );
		assertTrue( user.listRoles().contains( role ));
		
	}

	@Test
	public void testAddRoleNull() {
		user.addRole( null );
		assertEquals( 0, user.listRoles().size() );
	}

	@Test
	public void testRemoveRole() {
		Role role = RoleFactory.createRole();
		user.addRole( role );
		user.removeRole( role );

		assertEquals( 0, user.listRoles().size() );
	}

	@Test
	public void testClearRoles() {
		user.addRole( RoleFactory.createRole() );
		user.clearRoles();

		assertEquals( 0, user.listRoles().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddRole() {
		user.listRoles().add( RoleFactory.createRole() );
	}

	//
	// Qualifications
	//
	

	@Test
	public void testListQualifications() {
		assertEquals( 0, user.listQualifications().size() );
	}
	
	@Test
	public void testAddQualification() {
		Qualification qualification = QualificationFactory.createQualification();
		user.addQualification( qualification );

		assertEquals( 1, user.listQualifications().size() );
		assertTrue( user.listQualifications().contains( qualification ));
		
	}

	@Test
	public void testAddQualificationNull() {
		user.addQualification( null );
		assertEquals( 0, user.listQualifications().size() );
	}

	@Test
	public void testRemoveQualification() {
		Qualification qualification = QualificationFactory.createQualification();
		user.addQualification( qualification );
		user.removeQualification( qualification );

		assertEquals( 0, user.listQualifications().size() );
	}

	@Test
	public void testClearQualifications() {
		user.addQualification( QualificationFactory.createQualification() );
		user.clearQualifications();

		assertEquals( 0, user.listQualifications().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddQualification() {
		user.listQualifications().add( QualificationFactory.createQualification() );
	}

	
	@Test
	public void testPassword() {
		String password = "abcdefgh";
		PasswordHash passwordHash = new PasswordHash();
		user.setPassword( passwordHash.createPasswordKey( password ));
		
		assertTrue( user.isValidPassword( password ));
		assertFalse( user.isValidPassword( "ijklmnopq" ));
	}


}
