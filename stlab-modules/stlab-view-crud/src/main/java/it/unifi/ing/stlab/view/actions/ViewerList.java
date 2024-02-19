package it.unifi.ing.stlab.view.actions;

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

import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;

@Named
@RequestScoped
public class ViewerList extends Navigator {

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;	
	
	@Inject
	private Conversation conversation;

	@Inject
	private ViewerFilter viewerFilter;

	//
	// EJB injections
	//
	@Inject
	private ViewerDao viewerDao;

	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	@PostConstruct
	public void init() {
		setNavigationStatus( viewerFilter );

		refreshCurrentPage();
	}

	@Produces
	@RequestScoped
	@Named( "viewerResults" )
	public List<Viewer> getResultList() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<Viewer>();

		return viewerDao.find( viewerFilter, getOffset(), getLimit() );
	}

	public Boolean isRemovable( Long id ) {
		return !viewerDao.checkForeignKeyRestrictions( id );
	}
	
	//
	// navigation methods
	//
	public String addNew() {
		conversation.begin();
		return "add-new";
	}

	public String addNewDsl() {
		conversation.begin();
		return "add-new-dsl";
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
		viewerDao.delete( id );

		message( FacesMessage.SEVERITY_INFO, "Viewer successfully deleted!", true );
		return "delete";		
	}
	

	//
	// get methods
	//
	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = viewerDao.count( viewerFilter );
		}
		return itemCount;
	}

	public String getSelection() {
		return selection;
	}
	
	
	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}	
}
