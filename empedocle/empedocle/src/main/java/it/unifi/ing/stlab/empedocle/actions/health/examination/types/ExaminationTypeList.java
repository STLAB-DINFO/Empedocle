package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

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

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.navigation.Navigator;

@Named
@RequestScoped
public class ExaminationTypeList extends Navigator {

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@Inject
	private ExaminationTypeFilter examinationTypeFilter;

	//
	// EJB injections
	//
	@Inject
	private ExaminationTypeDao examinationTypeDao;

	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = examinationTypeDao.count( examinationTypeFilter );
		}
		return itemCount;
	}

	@PostConstruct
	public void init() {
		setNavigationStatus( examinationTypeFilter );

		refreshCurrentPage();
	}

	@Produces
	@RequestScoped
	@Named( "examinationTypeResults" )
	public List<ExaminationType> getResultList() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<ExaminationType>();

		return examinationTypeDao.find( examinationTypeFilter, getOffset(), getLimit() );
	}

	//
	// get methods
	//
	public String getSelection() {
		return selection;
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

	public String copy( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "copy";
	}

	public String edit( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "edit";
	}

	public String delete( Long id ) {
		examinationTypeDao.delete( id );

		message( FacesMessage.SEVERITY_INFO, "Visit Structure successfully deleted!", true );
		return "delete";
	}

	public Boolean isRemovable( Long id ) {
		return !examinationTypeDao.isUsed( id );
	}

	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
