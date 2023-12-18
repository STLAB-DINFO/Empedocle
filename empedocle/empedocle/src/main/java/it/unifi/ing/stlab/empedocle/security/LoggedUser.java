package it.unifi.ing.stlab.empedocle.security;

import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.users.model.User;

import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoggedUser {

	@Inject
	private ExternalContext context;

	@Inject
	private UserDao userDao;
	
	@Inject
	private StaffDao staffDao;	
	
	private User user;	
	
	public User getUser() {
		if ( user == null ) {
			initUser();
		}
		return user;
	}
	 
	public String getUsername() {
		if ( context.getUserPrincipal() == null ) return null;

		return context.getUserPrincipal().getName();
	}
	
	private void initUser() {
		if ( context.getUserPrincipal() == null ) return;
	
		user = userDao.findByUsername( context.getUserPrincipal().getName() );
	}
	
	public boolean hasRole( RoleType type ) {
		if ( getUser() == null ) return false;
		
		return getUser().hasRole( type.toString() );
	}
	
	public Qualification getUserQualification() {
		Set<Qualification> result = userDao.listUserQualifications(getUser().getId());
		
		if ( result.size() != 1 ) 
			throw new IllegalStateException( "number of qualifications != 1" );
		
		return result.iterator().next();
	}
	
	public Set<Agenda> getAgendas() {
		Staff result = staffDao.fetchByUsername( context.getUserPrincipal().getName() );
		
		return result.listAgendas();
	}
}

