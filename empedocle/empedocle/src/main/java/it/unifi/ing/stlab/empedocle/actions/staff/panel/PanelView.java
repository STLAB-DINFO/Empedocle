package it.unifi.ing.stlab.empedocle.actions.staff.panel;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.Conversation;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.actions.staff.PasswordGenerator;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.users.model.PasswordHash;

@Named
@ViewScoped
public class PanelView implements Serializable {

	private static final long serialVersionUID = -884448372427657742L;

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;

	@Inject
	private Conversation conversation;

	@PersistenceContext
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

	private String oldPassword;
	private String newPassword;
	private String repeatedPassword;
	
	
	@PostConstruct
	public void init() {
		current = staffDao.fetchByUsername( loggedUser.getUsername() );
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

	public void validateOldPassword( FacesContext context, UIComponent toValidate, Object value ) {
		PasswordHash pHash = new PasswordHash();
		String encrypted = pHash.createPasswordKey( (String) value );

		if ( !encrypted.equals( current.getUser().getPassword() ) ) {
			( (UIInput) toValidate ).setValid( false );

			message( FacesMessage.SEVERITY_ERROR, "La password fornita non è corretta!", false );
		}
	}

	public void validateNewPassword( FacesContext context, UIComponent toValidate, Object value ) {
		if ( newPassword == null || !newPassword.equals( value ) ) {
			( (UIInput) toValidate ).setValid( false );

			message( FacesMessage.SEVERITY_ERROR, "Le password inserite sono diverse!", false );
		}
	}

	public void savePassword() {
		PasswordGenerator tools = new PasswordGenerator();
		current.getUser().setPassword( tools.generateEncryptedPassword( newPassword ) );

		try {
			utx.begin();
			entityManager.merge( current );
			utx.commit();
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
		clear();

		message( FacesMessage.SEVERITY_INFO, "Password cambiata con successo!", true );
	}

	public void clear( String componentId ) {
		clear();

		resetUIComponents( facesContext.getViewRoot().findComponent( componentId ) );
	}
	
	
	//
	// getter & setter methods
	//
	public Staff getCurrent() {
		return current;
	}
	
	public String getFrom() {
		return from;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	public boolean hasOldPassword() {
		return oldPassword != null;
	}
	public void setOldPassword( String oldPassword ) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
	public boolean hasNewPassword() {
		return newPassword != null;
	}
	public void setNewPassword( String newPassword ) {
		this.newPassword = newPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public boolean hasRepeatedPassword() {
		return repeatedPassword != null;
	}
	public void setRepeatedPassword( String repeatedPassword ) {
		this.repeatedPassword = repeatedPassword;
	}

	public boolean hasAgendas() {
		return !current.listAgendas().isEmpty();
	}


	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}

	private void clear() {
		this.oldPassword = null;
		this.newPassword = null;
		this.repeatedPassword = null;
	}
	
	private void resetUIComponents( UIComponent component ) {
		if ( component != null ) {
			List<UIComponent> children = component.getChildren();
			for ( UIComponent child : children ) {
				resetUIComponents( child );
			}

			if ( component instanceof UIInput )
				( (UIInput) component ).resetValue();
		}
	}
}
