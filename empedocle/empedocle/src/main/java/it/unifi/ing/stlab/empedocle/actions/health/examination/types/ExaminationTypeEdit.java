package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.health.AuthorizationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ViewerUseFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Authorization;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationOperation;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;
import it.unifi.ing.stlab.empedocle.presentation.SelectionBean;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.users.dao.QualificationDao;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;

@Named
@ConversationScoped
public class ExaminationTypeEdit implements Serializable {

	private static final long serialVersionUID = -794160678938937112L;

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
	private ExaminationTypeDao examTypeDao;

	@Inject
	private TypeDao typeDao;

	@Inject
	private ViewerDao viewerDao;

	@Inject
	private QualificationDao qualificationDao;

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
	private ExaminationType current;

	private TypeSuggestion typeSuggestion;
	private ViewerSuggestion viewerSuggestion;
	private AgendaSuggestion agendaSuggestion;
	
	private SelectionBean<Agenda> agendaSelection;
	private SelectionBean<ViewerUse> viewerUseSelection;
	private SelectionBean<Authorization> authorizationSelection;

	private Long importFromExamTypeId;

	@PostConstruct
	public void init() {
		initSelectionLists();
		
		if ( isNew() ) {
			current = ExaminationTypeFactory.createExaminationType();
		} else if ( isEdit() ) {
			try {
				current = examTypeDao.findById( Long.parseLong( id ) );
			} catch ( Exception e ) {
				throw new RuntimeException( e );
			}
		} else if ( isCopy() ) {
			current = clone( examTypeDao.findById( Long.parseLong( id ) ) );
		}

		initTypeSuggestion();
		initViewerSuggestion();
		initAgendaSuggestion();
		
		agendaSelection
				.addSelectedEntities( listAgendas() );
		viewerUseSelection
				.addSelectedEntities( listViewerUses() );
		authorizationSelection
				.addSelectedEntities( listAuthorization() );
	}
	

	//
	// agenda
	//
	public void addAgenda() {
		agendaSelection.toggleActive();
	}

	public void removeAgenda( Agenda a ) {
		agendaSelection.remove( a );
		a.setExaminationType( null );
		agendaDao.update( a );
		
		if ( agendaSelection.getSelectedEntities().isEmpty() )
			addAgenda();
	}

	public void confirmAgenda() {
		Agenda entity = agendaSelection.getEntity();
		entity.setExaminationType( current );
		
		agendaSelection.getSelectedEntities().add( entity );
		agendaSelection.reset();
		agendaSuggestion.clear();
	}

	public Boolean renderAddAgenda() {
		List<Agenda> unusedAgendas = agendaDao.findUnusedAgendasBySuggestion( "", 0 );
		unusedAgendas.removeAll( agendaSelection.getSelectedEntities() );
		
		return !agendaSelection.getActive() && !unusedAgendas.isEmpty();
	}


	//
	// viewer uses
	//
	public void addViewerUse() {
		viewerUseSelection.toggleActive();
	}

	public void removeViewerUse( ViewerUse vu ) {
		viewerUseSelection.remove( vu );
		
		if ( viewerUseSelection.getSelectedEntities().isEmpty() )
			addViewerUse();
	}

	public void confirmViewerUse() {
		ViewerUse entity = viewerUseSelection.getEntity();
		
		viewerUseSelection.getSelectedEntities().add( entity );
		viewerUseSelection.reset();
		viewerSuggestion.clear();
	}

	public Boolean renderAddViewerUse() {
		return !viewerUseSelection.getActive();
	}	
	

	//
	// authorizations
	//
	public void addAuthorization() {
		authorizationSelection.toggleActive();
	}

	public void removeAuthorization( Authorization a ) {
		authorizationSelection.remove( a );
		
		if ( authorizationSelection.getSelectedEntities().isEmpty() )
			addAuthorization();
	}

	public void confirmAuthorization() {
		Authorization entity = authorizationSelection.getEntity();
		
		authorizationSelection.getSelectedEntities().add( entity );
		authorizationSelection.reset();
	}

	public Boolean renderAddAuthorization() {
		return !authorizationSelection.getActive();
	}	

	
	//
	// import methods
	//
	public List<SelectItem> listExamTypeImport() {
		List<SelectItem> results = new ArrayList<SelectItem>();
		
		List<ExaminationType> examTypes;
		if( isEdit() )
			examTypes = examTypeDao.findWithAgendas( current.getId() );
		else
			examTypes = examTypeDao.findWithAgendas();
		
		for ( ExaminationType et : examTypes ) {
			results.add( new SelectItem( et.getId(), et.getName() ) );
		}

		return results;
	}

	public void importAgendas() {
		List<Agenda> results = listAgendasFromExamTypeId();
		
		if( results != null ) {
			for( Agenda agenda : results ) {
				agenda.setExaminationType( current );
				agendaSelection.getSelectedEntities().add( agenda );
			}
			resetImportFromExamTypeId();
		}
	}

	public List<Agenda> listAgendasFromExamTypeId() {
		if ( importFromExamTypeId == null )
			return null;

		return agendaDao.findByExaminationTypeId( importFromExamTypeId );
	}

	public void resetImportFromExamTypeId() {
		importFromExamTypeId = null;
	}

	public Long getImportFromExamTypeId() {
		return importFromExamTypeId;
	}

	public void setImportFromExamTypeId( Long fromId ) {
		this.importFromExamTypeId = fromId;
	}

	
	//
	// navigation methods
	//
	public String save() {
		conversation.end();

		try {
			updateViewerUses();		
			updateAuthorizations();			

			if ( isNew() || isCopy() ) {
				examTypeDao.save( current );
			} else if ( isEdit() ) {
				examTypeDao.update( current );
			}
			
			updateAgendas();

			message( FacesMessage.SEVERITY_INFO, "Visit Structure successfully saved!", true );
		} catch ( EJBTransactionRolledbackException e ) {
			Throwable t = e.getCause();

			while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
				t = t.getCause();
			}

			if ( t instanceof ConstraintViolationException ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Unable to perform the save: name '" + current.getName()
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
	public ExaminationType getCurrent() {
		return current;
	}
	
	public boolean isNew() {
		return id == null;
	}

	public String getId() {
		return id;
	}

	public boolean getCopy() {
		return Boolean.valueOf( copy );
	}

	public String getFrom() {
		return from;
	}
	
	public SelectionBean<Agenda> getAgendaSelection() {
		return agendaSelection;
	}	
	
	public SelectionBean<ViewerUse> getViewerUseSelection() {
		return viewerUseSelection;
	}	
	
	public SelectionBean<Authorization> getAuthorizationSelection() {
		return authorizationSelection;
	}	

	public TypeSuggestion getTypeSuggestion() {
		return typeSuggestion;
	}

	public ViewerSuggestion getViewerSuggestion() {
		return viewerSuggestion;
	}

	public AgendaSuggestion getAgendaSuggestion() {
		return agendaSuggestion;
	}

	public List<SelectItem> getQualifications() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for ( Qualification q : qualificationDao.findBySuggestion( "", 0 ) )
			selectItems.add( new SelectItem( q, q.getName() ) );

		return selectItems;
	}

	public List<SelectItem> getExaminationContexts() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for ( ExaminationTypeContext ec : ExaminationTypeContext.values() )
			selectItems.add( new SelectItem( ec, ec.getName() ) );

		return selectItems;
	}

	public List<SelectItem> getExaminationOperations() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		for ( ExaminationOperation eo : ExaminationOperation.values() )
			selectItems.add( new SelectItem( eo, eo.getName() ) );

		return selectItems;
	}

	//
	// Utility class for suggestion of type
	//
	public class TypeSuggestion implements SuggestionInterface {

		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			for ( Type t : typeDao.findBySuggestion( suggestion, 0 ) )
				result.add( new SelectItem( t.getUuid(), t.getName() ) );

			return result;
		}

		public String getSuggestion() {
			return suggestion;
		}
		public void setSuggestion( String suggestion ) {
			this.suggestion = suggestion;
		}
	}

	
	//
	// Utility class for suggestion of viewer
	//
	public class ViewerSuggestion implements SuggestionInterface {

		private String suggestion;
		
		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			for ( Viewer v : viewerDao.findBySuggestion( suggestion, 0 ) )
				result.add( new SelectItem( v.getUuid(), v.getName() ) );

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
	// Utility class for suggestion of agenda
	//
	public class AgendaSuggestion implements SuggestionInterface {
		
		private String suggestion;

		@Override
		public List<SelectItem> autocomplete( String suggestion ) {
			List<SelectItem> result = new ArrayList<SelectItem>();
			List<Agenda> agendas = agendaDao.findUnusedAgendasBySuggestion( suggestion, 0 );
			agendas.removeAll( agendaSelection.getSelectedEntities() );

			for ( Agenda a : agendas ) {
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
	// private methods
	//
	public boolean isEdit() {
		return id != null && !Boolean.valueOf( copy );
	}

	private boolean isCopy() {
		return id != null && Boolean.valueOf( copy );
	}
	
	private List<Agenda> listAgendas() {
		return isEdit() ? agendaDao.findByExaminationTypeId( Long.parseLong( id ) )
				: new ArrayList<Agenda>();
	}
	
	private Collection<Authorization> listAuthorization() {
		return ( isEdit() || isCopy() ) ? current.listAuthorizations()
				: new ArrayList<Authorization>();
	}
	
	private List<ViewerUse> listViewerUses() {
		List<ViewerUse> vu = new ArrayList<ViewerUse>( current.listViewers() );
		Collections.sort( vu, new Comparator<ViewerUse>() {

			@Override
			public int compare( ViewerUse vu1, ViewerUse vu2 ) {
				String ctx1 = vu1.getContext() != null ? vu1.getContext().getName() : "zzzzzz";
				String ctx2 = vu2.getContext() != null ? vu2.getContext().getName() : "zzzzzz";

				int result = ctx1.compareTo( ctx2 );

				if ( result == 0 ) {
					String qlf1 = vu1.getQualification() != null ? vu1.getQualification().getName()
							: "zzzzzz";
					String qlf2 = vu2.getQualification() != null ? vu2.getQualification().getName()
							: "zzzzzz";
					result = qlf1.compareTo( qlf2 );
				}

				return result != 0 ? result : vu1.getUuid().compareTo( vu2.getUuid() );
			}

		} );
		
		return vu;
	}
	
	private void updateAgendas() {
		for( Agenda a : agendaSelection.getSelectedEntities() ) {
			agendaDao.update( a );
		}		
	}
	
	private void updateViewerUses() {
		current.getViewers().clear();
		for( ViewerUse vu : viewerUseSelection.getSelectedEntities() ) {
			current.addViewerUse( vu );
		}			
	}	
	
	private void updateAuthorizations() {
		current.getAuthorizations().clear();
		for( Authorization auth : authorizationSelection.getSelectedEntities() ) {
			current.addAuthorization( auth );
		}			
	}		

	private ExaminationType clone( ExaminationType src ) {
		ExaminationTypeCloner cloner = new ExaminationTypeCloner( src );
		return cloner.clone();
	}

	private void initTypeSuggestion() {
		typeSuggestion = new TypeSuggestion();
		if ( ( isEdit() || isCopy() ) && current.getType() != null )
			typeSuggestion.setSuggestion( current.getType().getName() );
	}
	
	private void initViewerSuggestion() {
		viewerSuggestion = new ViewerSuggestion();
	}
	
	private void initAgendaSuggestion() {
		agendaSuggestion = new AgendaSuggestion();
	}	
	
	private void initSelectionLists() {
		agendaSelection = new SelectionBean<Agenda>( AgendaFactory.createAgenda() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( AgendaFactory.createAgenda() );
			}
		};
		
		viewerUseSelection = new SelectionBean<ViewerUse>( ViewerUseFactory.createViewerUse() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( ViewerUseFactory.createViewerUse() );
			}
		};
		
		authorizationSelection = new SelectionBean<Authorization>( AuthorizationFactory.createAuthorization() ) {

			@Override
			public void reset() {
				super.setActive( false );
				super.setEntity( AuthorizationFactory.createAuthorization() );
			}
		};		
	}

	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
