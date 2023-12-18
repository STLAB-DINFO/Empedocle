package it.unifi.ing.stlab.empedocle.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.test.FieldUtils;

import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

public class LoginBeanTest {

	protected HttpServletRequest request;
	protected ExternalContext context;
	protected LoginBean loginBean;
	
	@Before
	public void setUp() {
		request = mock( HttpServletRequest.class );
		context = mock( ExternalContext.class );
		when( context.getRequest() ).thenReturn( request );
		
		loginBean = new LoginBean();
		FieldUtils.assignField( loginBean, "context", context );
	}
	

	@Test
	public void testLogin() throws ServletException {
		loginBean.login( "admin", "password" );
		
		verify( request ).login( eq( "admin"), eq( "password" ));
	}

	
	@Test
	public void testLogout() throws ServletException {
		loginBean.logout();
		
		verify( request ).logout();
	}
	
	
	@Test
	public void testLoggedInTrue() {
		Principal principal = mock( Principal.class );
		when( context.getUserPrincipal() ).thenReturn( principal );

		assertTrue( loginBean.isLoggedIn() );
	}
	
	
	@Test
	public void testLoggedInFalse() {
		when( context.getUserPrincipal() ).thenReturn( null );
		
		assertFalse( loginBean.isLoggedIn() );
	}
}

