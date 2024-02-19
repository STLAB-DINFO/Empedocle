package it.unifi.ing.stlab.empedocle.actions.agendas;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

@Named
@ConversationScoped
public class AgendaEdit implements Serializable {

	private static final long serialVersionUID = -5578714165914879861L;

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	//
	// EJB injections
	//
	@Inject
	private AgendaDao agendaDao;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	@Inject
	@HttpParam( "from" )
	private String from;

	//
	// Local attributes
	//
	private Agenda current;

	@PostConstruct
	public void init() {
		// new
		if ( isNew() ) {
			current = AgendaFactory.createAgenda();
		}
		// edit
		else {
			current = agendaDao.findById( Long.parseLong( id ) );
		}
	}

	public boolean isNew() {
		return id == null;
	}

	//
	// navigation methods
	//
	public String save() {
		conversation.end();

		try {
			if ( isNew() ) {
				agendaDao.save( current );
			}
			// edit
			else {
				agendaDao.update( current );
			}

			message( FacesMessage.SEVERITY_INFO, "Agenda successfully saved!", true );
		} catch ( EJBTransactionRolledbackException e ) {
			Throwable t = e.getCause();
			
			while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
				t = t.getCause();
			}
			
			if ( t instanceof ConstraintViolationException ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Unable to perform the save: code '" + current.getCode()
								+ "' already in use!",
						true );
			}
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Error during saving!", true );
		}
		
		return "save";
	}

	public String cancel() {
		conversation.end();
		return "cancel";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public Agenda getCurrent() {
		return current;
	}

	//
	// private methods
	//

	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
