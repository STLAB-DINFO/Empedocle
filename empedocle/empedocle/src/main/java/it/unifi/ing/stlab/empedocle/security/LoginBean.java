package it.unifi.ing.stlab.empedocle.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named 
@RequestScoped
public class LoginBean {
	
	@Inject
	private ExternalContext context;
	
	public boolean isLoggedIn() {
		return context.getUserPrincipal() != null;
	}


	public void login ( String userid, String password ) throws ServletException {
		HttpServletRequest request = getRequest();
				
		request.login( userid, password );
	}

	public String logout() throws ServletException {
		context.invalidateSession();
		getRequest().logout();
		return "logout";
	}
	
	
	private HttpServletRequest getRequest() {
		return (HttpServletRequest)context.getRequest();
	}
	
}