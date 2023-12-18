package it.unifi.ing.stlab.view.actions;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerBean;
import it.unifi.ing.stlab.view.dao.ViewerDao;

@Named
@ViewScoped
public class ViewerView extends ViewerController {

	private static final long serialVersionUID = -4356647221068958207L;

	//
	// CDI injections
	//
	@Inject
	private Conversation conversation;

	//
	// EJB injections
	//
	@Inject
	private ViewerDao viewerDao;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	//
	// Local attributes
	//
	private ViewerBean current;


	@PostConstruct
	public void init() {
		current = new ViewerBean( viewerDao.fetchById( Long.parseLong( id ) ) );
	}
	
	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	public String edit() {
		conversation.begin();
		return "edit";
	}
	
	
	//
	// get methods
	//
	public String getId() {
		return id;
	}

	public ViewerBean getCurrent() {
		return current;
	}
}
