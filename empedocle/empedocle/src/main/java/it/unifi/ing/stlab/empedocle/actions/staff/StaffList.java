package it.unifi.ing.stlab.empedocle.actions.staff;

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

import it.unifi.ing.stlab.empedocle.actions.util.time.TimeUtils;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.navigation.Navigator;

@Named
@RequestScoped
public class StaffList extends Navigator {

	//
	// CDI Injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@Inject
	private LoggedUser loggedUser;

	@Inject
	protected StaffFilter staffFilter;

	//
	// EJB injections
	//
	@Inject
	private StaffDao staffDao;

	//
	// Local attributes
	//
	private String selection;
	private Integer itemCount;

	@PostConstruct
	public void init() {
		setNavigationStatus( staffFilter );

		refreshCurrentPage();
	}

	@Produces
	@RequestScoped
	@Named( "staffResults" )
	public List<Staff> getResultList() {
		if ( getItemCount().intValue() == 0 )
			return new ArrayList<Staff>();

		return staffDao.find( staffFilter, getOffset(), getLimit() );
	}

	public Boolean isItMe( Staff current ) {
		return loggedUser.getUser().equals( current.getUser() );
	}

	public Boolean isRemovable( Staff current ) {
		return !staffDao.checkForeignKeyRestrictions( current );
	}

	public Boolean isActive( Staff current ) {
		return !current.getUser().getIsDeprecated();
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

	public String copy( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "copy";
	}

	public String delete( Long id ) {
		staffDao.delete( id );

		message( FacesMessage.SEVERITY_INFO, "Utente cancellato con successo!", true );
		return "delete";
	}

	public String enable( Staff current ) {
		current.getPhenomenon()
				.setTimeRange( TimeUtils.timeRange( TimeUtils.now(), TimeUtils.infinity() ) );

		PasswordGenerator tools = new PasswordGenerator();
		current.getUser().setPassword( tools.generateEncryptedDefaultPassword() );
		current.getUser().setIsDeprecated( false );

		staffDao.update( current );

		message( FacesMessage.SEVERITY_INFO, "Utente riattivato con successo!", true );
		return "enable-disable";
	}

	public String disable( Staff current ) {
		current.getPhenomenon()
				.setTimeRange( TimeUtils.timeRange(
						TimeUtils.getBeginning( current.getPhenomenon().getTimeRange() ),
						TimeUtils.now() ) );

		PasswordGenerator tools = new PasswordGenerator();
		current.getUser().setPassword( tools.generateEncryptedRandomPassword() );
		current.getUser().setIsDeprecated( true );

		staffDao.update( current );

		message( FacesMessage.SEVERITY_INFO, "Utente disattivato con successo!", true );
		return "enable-disable";
	}

	public String resetPassword( Staff current ) {
		PasswordGenerator tools = new PasswordGenerator();
		current.getUser().setPassword( tools.generateEncryptedDefaultPassword() );
		staffDao.update( current );

		message( FacesMessage.SEVERITY_INFO, "Password resettata con successo!", true );
		return "reset";
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
			itemCount = staffDao.count( staffFilter );
		}
		return itemCount;
	}

	public String getRowClasses() {
		StringBuilder sb = new StringBuilder();
		boolean appendSeparator = false;

		for ( Staff s : getResultList() ) {
			if ( appendSeparator )
				sb.append( "," );
			else
				appendSeparator = true;

			if ( !s.getUser().getIsDeprecated() )
				sb.append( "row-base" );
			else
				sb.append( "row-deprecated" );
		}

		return sb.toString();
	}

	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
