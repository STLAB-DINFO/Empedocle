package it.unifi.ing.stlab.empedocle.actions.menu;

import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.users.model.RoleType;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class HeaderMenu {
	
	//
	// CDI injections
	//
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoggedUser loggedUser;
	
	public void endConversation() {
		if(!conversation.isTransient()) {
			conversation.end();
		}
	}
	
	public boolean checkRoleFor( String operation ) {

		switch ( operation ) {
		case "examination-list":
			return true;
			
		case "configurability-item":
			return loggedUser.hasRole( RoleType.DOMAIN_EXPERT ) || loggedUser.hasRole( RoleType.ADMINISTRATOR );
			
		case "agenda-list":
		case "type-list":
		case "view-list":
		case "fact-query-list":
		case "examinationtype-list":
			return loggedUser.hasRole( RoleType.DOMAIN_EXPERT ) ||  loggedUser.hasRole( RoleType.ADMINISTRATOR );
			
		case "staff-list":
			return loggedUser.hasRole( RoleType.ADMINISTRATOR );
			
		default:
			return false;
		}
	}
}
