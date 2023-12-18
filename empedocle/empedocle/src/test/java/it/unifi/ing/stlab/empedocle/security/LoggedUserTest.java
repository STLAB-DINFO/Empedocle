package it.unifi.ing.stlab.empedocle.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Role;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.users.model.User;

import java.security.Principal;

import javax.faces.context.ExternalContext;

import org.junit.Before;
import org.junit.Test;

public class LoggedUserTest {

	protected Principal principal;
	protected ExternalContext context;
	protected LoggedUser loggedUser;
	
	@Before
	public void setUp() {
		principal = mock( Principal.class );
		when( principal.getName() ).thenReturn( "mrossi" );
		
		context = mock( ExternalContext.class );

		Role role = RoleFactory.createRole();
		role.setName( RoleType.BASIC_USER.toString() );
		
		User user = UserFactory.createUser();
		user.setUserid( "mrossi" );
		user.setName( "Mario" );
		user.setSurname( "Rossi" );
		user.addRole( role );

		UserDao userDao = mock( UserDao.class );
		when( userDao.findByUsername( eq( "mrossi"))).thenReturn( user );
		
		loggedUser = new LoggedUser();
		FieldUtils.assignField(loggedUser, "context", context );
		FieldUtils.assignField(loggedUser, "userDao", userDao );
	}
	
	@Test
	public void testLoggeUser() {
		when( context.getUserPrincipal()).thenReturn( principal );

		assertNotNull( loggedUser.getUser() );
	}

	@Test
	public void testLoggeUserFail() {
		when( context.getUserPrincipal()).thenReturn( null );

		assertNull( loggedUser.getUser() );
	}

	@Test
	public void testHasRole() {
		when( context.getUserPrincipal()).thenReturn( principal );

		assertTrue( loggedUser.hasRole( RoleType.BASIC_USER ));
	}

	@Test
	public void testHasRoleNotFound() {
		when( context.getUserPrincipal()).thenReturn( principal );

		assertFalse( loggedUser.hasRole( RoleType.ADMINISTRATOR));
	}
	
	@Test
	public void testHasRoleNotLogged() {
		assertFalse( loggedUser.hasRole( RoleType.BASIC_USER ));
	}

	@Test
	public void testHasRoleNull() {
		assertFalse( loggedUser.hasRole( null ));
	}
}

