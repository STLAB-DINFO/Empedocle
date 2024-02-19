package it.unifi.ing.stlab.reflection.actions;

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
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Type;

// TODO testare TypeList
@Named
@RequestScoped
public class TypeList extends Navigator {

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@Inject
	protected TypeFilter typeFilter;

	//
	// EJB injections
	//
	@Inject
	private TypeDao typeDao;

	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	
	private String selectedType;

	@PostConstruct
	public void init() {
		setNavigationStatus( typeFilter );

		refreshCurrentPage();
	}

	@Produces
	@RequestScoped
	@Named( "typeResults" )
	public List<Type> getResultList() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<Type>();

		return typeDao.find( typeFilter, getOffset(), getLimit() );
	}
	
	public Boolean isRemovable( Long id ) {
		return !typeDao.checkForeignKeyRestrictions( id );
	}

	//
	// navigation methods
	//
	public String addNew() {
		conversation.begin();
		return "add-new";
	}

	public String addNewDsl() {
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

	public String copy( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "copy";
	}
	
	public String delete( Long id ) {
		typeDao.delete( id );

		message( FacesMessage.SEVERITY_INFO, "Type successfully deleted!", true );
		return "delete";
	}	

	
	//
	// get & set methods
	//
	public String getSelection() {
		return selection;
	}

	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = typeDao.count( typeFilter );
		}
		return itemCount;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType( String type ) {
		this.selectedType = type;
	}
	
	
	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}	
}
