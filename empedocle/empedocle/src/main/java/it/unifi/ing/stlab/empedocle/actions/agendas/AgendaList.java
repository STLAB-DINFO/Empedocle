package it.unifi.ing.stlab.empedocle.actions.agendas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.navigation.Navigator;

@Named
@RequestScoped
public class AgendaList extends Navigator {

	//
	// CDI Injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@Inject
	protected AgendaFilter agendaFilter;

	//
	// EJB injections
	//
	@Inject
	private AgendaDao agendaDao;

	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	@PostConstruct
	public void init() {
		setNavigationStatus( agendaFilter );

		refreshCurrentPage();
	}

	@Produces
	@RequestScoped
	@Named( "agendaResults" )
	public List<Agenda> getResults() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<Agenda>();

		return agendaDao.find( agendaFilter, getOffset(), getLimit() );
	}

	public Boolean isRemovable( Long id ) {
		return !agendaDao.checkForeignKeyRestrictions( id );
	}

	//
	// navigation methods
	//
	public String addNew() {
		conversation.begin();
		return "add-new";
	}

	public String view( Long id ) {
		selection = id.toString();
		return "view";
	}

	public String edit( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "edit";
	}

	public String delete( Long id ) {
		agendaDao.delete( id );

		message( FacesMessage.SEVERITY_INFO, "Agenda cancellata con successo!", true );
		return "delete";
	}

	//
	// get methods
	//
	public String getSelection() {
		return selection;
	}

	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = agendaDao.count( agendaFilter );
		}
		return itemCount;
	}

	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
