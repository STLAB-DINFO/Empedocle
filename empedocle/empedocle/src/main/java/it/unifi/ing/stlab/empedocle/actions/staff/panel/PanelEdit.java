package it.unifi.ing.stlab.empedocle.actions.staff.panel;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;

@Named
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class PanelEdit implements Serializable {

	private static final long serialVersionUID = -8052287906308373688L;

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;

	@Resource
	private UserTransaction utx;

	@Inject
	private LoggedUser loggedUser;

	//
	// EJB injections
	//
	@Inject
	private StaffDao staffDao;
	

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "from" )
	private String from;

	//
	// local attributes
	//
	private Staff current;

	
	@PostConstruct
	public void init() {
		try {
			utx.begin();
			current = staffDao.fetchByUsername( loggedUser.getUsername() );
			utx.commit();
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	//
	// agenda methods
	//
	public boolean hasAgendas() {
		return !current.listAgendas().isEmpty();
	}
	public void markAsFavorite( Agenda a ) {
		current.addFavoriteAgenda( a );
	}
	public void removeFavorite( Agenda a ) {
		current.removeFavoriteAgenda( a );
	}
	public boolean isFavoriteAgenda( Agenda a ) {
		return current.listFavoriteAgendas().contains( a );
	}
	

	//
	// navigation methods
	//
	public String cancel() {
		conversation.end();
		return "save-cancel";
	}

	public String save() {
		conversation.end();

		try {
			utx.begin();
			staffDao.update( current );
			utx.commit();

			message( FacesMessage.SEVERITY_INFO, "Profile successfully updated!", true );
			return "save-cancel";
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Error during saving!", true );
			throw new RuntimeException( e );
		}
	}


	//
	// get methods
	//
	public Staff getCurrent() {
		return current;
	}

	public String getFrom() {
		return from;
	}
	
	public String getRowStyleClasses() {
		StringBuffer buffer = new StringBuffer();

		boolean first = true;
		for ( Agenda agenda : current.listAgendasOrdered() ) {
			if ( first ) {
				first = false;
			} else {
				buffer.append( ", " );
			}
			buffer.append( isFavoriteAgenda( agenda ) ? "success" : "base" );
		}
		return buffer.toString();
	}

	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
