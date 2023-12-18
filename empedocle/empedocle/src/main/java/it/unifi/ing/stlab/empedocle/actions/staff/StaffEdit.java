package it.unifi.ing.stlab.empedocle.actions.staff;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.StaffFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.presentation.SelectionBean;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.users.dao.QualificationDao;
import it.unifi.ing.stlab.users.dao.RoleDao;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.Role;

@Named
@ConversationScoped
public class StaffEdit implements Serializable {

	private static final long serialVersionUID = 6847804994159848595L;

	//
	// CDI injections
	//
	@Inject
	private Conversation conversation;

	@Inject
	private FacesContext facesContext;

	//
	// EJB injections
	//
	@Inject
	private StaffDao staffDao;

	@Inject
	private QualificationDao qualificationDao;

	@Inject
	private RoleDao roleDao;

	@Inject
	private AgendaDao agendaDao;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	@Inject
	@HttpParam( "copy" )
	private String copy;

	@Inject
	@HttpParam( "from" )
	private String from;

	//
	// Local attributes
	//
	private Staff current;

	private SelectionBean<Role> roleSelection;
	private SelectionBean<Qualification> qualificationSelection;
	private SelectionBean<Agenda> agendaSelection;

	private AgendaSuggestion agendaSuggestion;
	private DefaultAgendaSuggestion defaultAgendaSuggestion;

	@PostConstruct
	public void init() {
		initSelectionLists();

		if ( isNew() )
			current = initNewStaff();
		else if ( isEdit() )
			current = staffDao.fetchById( Long.parseLong( id ) );
		else if ( isCopy() )
			current = clone( staffDao.fetchById( Long.parseLong( id ) ) );

		roleSelection.addSelectedEntities( current.getUser().listRoles() );
		qualificationSelection.addSelectedEntities( current.getUser().listQualifications() );
		agendaSelection.addSelectedEntities( current.listAgendasOrdered() );

		if ( qualificationSelection.getSelectedEntities().isEmpty() )
			addQualification();
		if ( roleSelection.getSelectedEntities().isEmpty() )
			addRole();
		if ( agendaSelection.getSelectedEntities().isEmpty() )
			addAgenda();

		initDefaultAgendaSuggestion();
		initAgendaSuggestion();
	}

	//
	// agenda
	//
	public void addAgenda() {
		agendaSelection.toggleActive();
	}

	public void removeAgenda( Agenda a ) {
		agendaSelection.remove( a );
		if ( a.equals( current.getDefaultAgenda() ) ) {
			current.setDefaultAgenda( null );
			defaultAgendaSuggestion.clear();
		}
		if ( agendaSelection.getSelectedEntities().isEmpty() )
			addAgenda();
	}

	public void confirmAgenda() {
		agendaSelection.getSelectedEntities().add( agendaSelection.getEntity() );
		agendaSelection.reset();
		agendaSuggestion.clear();
	}

	public Boolean renderAddAgenda() {
		return !agendaSelection.getActive();
	}

	//
	// qualification
	//
	public void addQualification() {
		qualificationSelection.toggleActive();
	}

	public void removeQualification( Qualification q ) {
		qualificationSelection.remove( q );
		if ( qualificationSelection.getSelectedEntities().isEmpty() )
			addQualification();
	}

	public void confirmQualification( AjaxBehaviorEvent event ) {
		qualificationSelection.getSelectedEntities().add( qualificationSelection.getEntity() );
		qualificationSelection.reset();
	}

	public Boolean renderAddQualification() {
		return !qualificationSelection.getActive()
				&& qualificationSelection.getSelectedEntities().isEmpty()
				&& !listAvailableQualifications().isEmpty();
	}

	public List<SelectItem> listAvailableQualifications() {
		List<Qualification> quals = qualificationDao.getAll();

		quals.removeAll( qualificationSelection.getSelectedEntities() );

		List<SelectItem> items = new ArrayList<SelectItem>();
		for ( Qualification q : quals ) {
			items.add( new SelectItem( q, q.getName() ) );
		}

		return items;
	}

	//
	// role
	//
	public void addRole() {
		roleSelection.toggleActive();
	}

	public void removeRole( Role r ) {
		roleSelection.remove( r );
		if ( roleSelection.getSelectedEntities().isEmpty() )
			addRole();
	}

	public void confirmRole( AjaxBehaviorEvent event ) {
		roleSelection.getSelectedEntities().add( roleSelection.getEntity() );
		roleSelection.reset();
	}

	public Boolean renderAddRole() {
		return !roleSelection.getActive() && !listAvailableRoles().isEmpty();
	}

	public List<SelectItem> listAvailableRoles() {
		List<Role> roles = roleDao.getAll();

		roles.removeAll( roleSelection.getSelectedEntities() );

		List<SelectItem> items = new ArrayList<SelectItem>();
		for ( Role r : roles ) {
			items.add( new SelectItem( r, r.getName() ) );
		}

		return items;
	}

	//
	// navigation methods
	//
	public String cancel() {
		conversation.end();
		return "cancel";
	}

	public String save() {
		updateCollections();
		conversation.end();
		
		try {
			if ( isNew() || isCopy() ) {
				Phenomenon p = PhenomenonFactory.createPhenomenon();
				updateStaffPhenomenon( p );
				current.setPhenomenon( p );
				staffDao.save( current );
			} else if ( isEdit() ) {
				updateStaffPhenomenon( current.getPhenomenon() );
	
				// workaround to set current.defaultAgenda == null when
				// defaultAgendaSuggestion is null
				// (AgendaConverter doesn't set the property when value is null)
				if ( defaultAgendaSuggestion.getSuggestion() == null )
					current.setDefaultAgenda( null );
	
				staffDao.update( current );
			}
	
			message( FacesMessage.SEVERITY_INFO, "Utente salvato con successo!", true );
		} catch ( EJBTransactionRolledbackException e ) {
			Throwable t = e.getCause();

			while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
				t = t.getCause();
			}

			if ( t instanceof ConstraintViolationException ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Impossibile effettuare il salvataggio: username '"
								+ current.getUser().getUserid() + "' già in uso!",
						true );
			}
		} catch ( Exception e ) {
			message( FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio!", true );
		}
		
		return "save";
	}

	//
	// get methods
	//
	public Staff getCurrent() {
		return current;
	}

	public String getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public boolean isNew() {
		return id == null;
	}

	public boolean getCopy() {
		return Boolean.valueOf( copy );
	}

	public SelectionBean<Role> getRoleSelection() {
		return roleSelection;
	}

	public SelectionBean<Qualification> getQualificationSelection() {
		return qualificationSelection;
	}

	public SelectionBean<Agenda> getAgendaSelection() {
		return agendaSelection;
	}

	public AgendaSuggestion getAgendaSuggestion() {
		return agendaSuggestion;
	}

	public DefaultAgendaSuggestion getDefaultAgendaSuggestion() {
		return defaultAgendaSuggestion;
	}

	//
	// private methods
	//
	private Staff clone( Staff src ) {
		Staff clone = initNewStaff();
		for ( Agenda a : src.listAgendas() ) {
			clone.addAgenda( a );
		}

		for ( Role r : src.getUser().listRoles() ) {
			clone.getUser().addRole( r );
		}

		for ( Qualification q : src.getUser().listQualifications() ) {
			clone.getUser().addQualification( q );
		}

		return clone;
	}

	private Staff initNewStaff() {
		Staff s = StaffFactory.createStaff();
		s.setUser( UserFactory.createUser() );

		PasswordGenerator tools = new PasswordGenerator();
		s.getUser().setPassword( tools.generateEncryptedDefaultPassword() );

		return s;
	}

	private void updateStaffPhenomenon( Phenomenon p ) {
		p.setName( current.getUser().getSurname() + " " + current.getUser().getName() );
	}

	private void initSelectionLists() {
		roleSelection = new SelectionBean<Role>( RoleFactory.createRole() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( RoleFactory.createRole() );
			}
		};

		qualificationSelection = new SelectionBean<Qualification>(
				QualificationFactory.createQualification() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( QualificationFactory.createQualification() );
			}
		};

		agendaSelection = new SelectionBean<Agenda>( AgendaFactory.createAgenda() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( AgendaFactory.createAgenda() );
			}
		};
	}

	private void initDefaultAgendaSuggestion() {
		defaultAgendaSuggestion = new DefaultAgendaSuggestion();
		if ( isEdit() && current.getDefaultAgenda() != null )
			defaultAgendaSuggestion.setSuggestion( current.getDefaultAgenda().toString() );
	}

	private void initAgendaSuggestion() {
		agendaSuggestion = new AgendaSuggestion();
	}

	private void updateCollections() {
		updateRoles();
		updateQualifications();
		updateAgendas();
	}

	private void updateRoles() {
		current.getUser().clearRoles();
		for ( Role r : roleSelection.getSelectedEntities() ) {
			current.getUser().addRole( r );
		}
	}

	private void updateQualifications() {
		current.getUser().clearQualifications();
		for ( Qualification q : qualificationSelection.getSelectedEntities() ) {
			current.getUser().addQualification( q );
		}
	}

	private void updateAgendas() {
		current.clearAgendas();
		for ( Agenda a : agendaSelection.getSelectedEntities() ) {
			current.addAgenda( a );
		}
	}

	public boolean isEdit() {
		return !isNew() && !Boolean.valueOf( copy );
	}

	private boolean isCopy() {
		return !isNew() && Boolean.valueOf( copy );
	}

	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}

	//
	// Internal class for agenda suggestion
	//
	public class AgendaSuggestion implements SuggestionInterface {

		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<Agenda> ags = agendaDao.findBySuggestion( suggestion, 0 );
			ags.removeAll( agendaSelection.getSelectedEntities() );

			for ( Agenda a : ags ) {
				result.add( new SelectItem( a.getUuid(), a.toString() ) );
			}

			return result;
		}

		public String getSuggestion() {
			return suggestion;
		}

		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}

		public void clear() {
			suggestion = null;
		}
	}

	//
	// Utility class for suggestion of default agenda
	//
	public class DefaultAgendaSuggestion implements SuggestionInterface {

		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();

			for ( Agenda a : agendaSelection.getSelectedEntities() ) {
				if ( a.toString().toLowerCase().contains( suggestion.toLowerCase() ) ) {
					result.add( new SelectItem( a.getUuid(), a.toString() ) );
				}
			}

			return result;
		}

		public String getSuggestion() {
			return suggestion;
		}

		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}

		public void clear() {
			suggestion = null;
		}
	}
}
