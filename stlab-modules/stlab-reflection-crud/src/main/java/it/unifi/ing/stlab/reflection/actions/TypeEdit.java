package it.unifi.ing.stlab.reflection.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;

import org.hibernate.exception.ConstraintViolationException;
import org.richfaces.event.DropEvent;
import org.richfaces.event.TreeSelectionChangeEvent;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.viewers.SuggestionInterface;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeBean;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeTreeNode;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.UnitDao;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.type.TypeCopyVisitor;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

@Named
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class TypeEdit extends TypeController {

	private static final long serialVersionUID = -6761242894326073899L;

	//
	// CDI injections
	//

	@Inject
	private Conversation conversation;
	
	@Inject
	private FacesContext facesContext;

	@EJB
	private TypeDao typeDao;

	@Inject
	@HttpParam( "id" )
	private String id;

	@Inject
	@HttpParam( "from" )
	private String from;

	@Inject
	@HttpParam( "copy" )
	private String copy;

	@Inject
	@HttpParam( "type" )
	private String selectedType;

	//
	// EJB injections
	//

	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;

	@Resource
	private UserTransaction utx;

	@EJB
	private UnitDao unitDao;

	//
	// Local attributes
	//

	private TypeBean current;

	private List<SelectItem> allUnits;
	private long nextLink = 0;

	private Type referencedType;

	private Date startDate;
	private Date endDate;

	private FactValue currentDefaultValue;

	private SuggestionInterface typeSuggestion;


	@PostConstruct
	public void init() {
		// edit
		if ( !isNew() ) {
			Type source = entityManager.find( Type.class, Long.parseLong( id ) );

			if ( isCopy() ) {
				TypeCopyVisitor v = new TypeCopyVisitor();
				source.accept( v );
				getEntityManager().persist( v.getResult() );
				current = new TypeBean( getEntityManager(), v.getResult() );

			} else {
				current = new TypeBean( getEntityManager(), source );

			}

			initCurrentTimeRange( source.getTimeRange() );

		} else {
			// crea nuovo
			Type type = null;
			if ( "textual".equals( selectedType ) ) {
				type = TypeFactory.createTextualType();
			} else if ( "enumerated".equals( selectedType ) ) {
				type = TypeFactory.createEnumeratedType();
			} else if ( "queried".equals( selectedType ) ) {
				type = TypeFactory.createQueriedType();
			} else if ( "quantitative".equals( selectedType ) ) {
				type = TypeFactory.createQuantitativeType();
			} else if ( "temporal".equals( selectedType ) ) {
				type = TypeFactory.createTemporalType();
			} else if ( "composite".equals( selectedType ) ) {
				type = TypeFactory.createCompositeType();
			}

			type.setReadOnly( false );
			type.setAnonymous( false );
			getEntityManager().persist( type );
			current = new TypeBean( getEntityManager(), type );
		}
	}

	public List<SelectItem> autocomplete( String suggestion ) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		for ( Type t : typeDao.findBySuggestion( suggestion, 10 ) ) {
			result.add( new SelectItem( t.getUuid(), t.getName() ) );
		}

		return result;
	}
	
	public boolean isNew() {
		return id == null;
	}
	
	public boolean isEdit() {
		return id != null && !Boolean.valueOf( copy );
	}
	
	private boolean isCopy() {
		return id != null && Boolean.valueOf( copy );
	}

	@Override
	public void selectionChanged( TreeSelectionChangeEvent selectionChangeEvent ) {
		syncCurrentDefaultValue();
		super.selectionChanged( selectionChangeEvent );

		referencedType = isCurrentLinkSet() ? getSelectedLink().getLink().getTarget() : null;

	}

	public void confirmReferencedType() throws Exception {
		if ( isCurrentLinkSet() ) {
			if ( referencedType != null ) {
				getSelectedLink().getLink()
						.assignTarget( entityManager.find( Type.class, referencedType.getId() ) );
				getSelectedLink().getLink().setName( referencedType.getName() );

			} else {
				getSelectedLink().getLink().assignTarget( null );
				getSelectedLink().getLink().setName( null );

			}

			// pulisco defaultValue
			getSelectedLink().clearDefaultValue();
			// pulisco il type selezionato
			clearSelectedType();

		}
	}

	public void closeSelection() {
		syncCurrentDefaultValue();
		setSelectedLink( null );
		setReferencedType( null );
		setCurrentDefaultValue( null );

	}

	// END new stuff

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public TypeBean getCurrent() {
		return current;
	}

	private void initCurrentTimeRange( TimeRange source ) {
		if ( source != null ) {
			if ( source.getLeft() != null ) {
				startDate = source.getLeft().getDate();

			}

			if ( source.getRight() != null ) {
				endDate = source.getRight().getDate();

			}
		}

	}

	public List<SelectItem> getAllUnits() {
		if ( allUnits == null ) {
			initAllUnits();
		}
		return allUnits;
	}

	private void initAllUnits() {
		allUnits = new ArrayList<SelectItem>();

		for ( Unit u : unitDao.findAll() ) {
			allUnits.add( new SelectItem( u.getUuid(), u.getName() ) );
		}
	}

	public void addNodeListener( DropEvent event ) {
		TypeTreeNode parent = findNode( (String) event.getDropValue() );
		Type parentType = typeOf( parent );

		// il type creato è null se voglio una reference
		String dragValue = (String) event.getDragValue();
		Type newType = createType( dragValue );
		if ( newType != null ) {
			newType.setAnonymous( true );
			entityManager.persist( newType );
		}

		TypeLink newLink = TypeLinkFactory.createLink();
		nextLink++;
		newLink.setName( dragValue + nextLink );
		newLink.setMin( new Integer( 1 ) );
		newLink.setMax( new Integer( 1 ) );
		newLink.setPriority( new Long( parentType.listChildren().size() + 1 ) );
		newLink.assignSource( parentType );
		newLink.assignTarget( newType );

		boolean leaf = ( newType == null
				|| !ClassHelper.instanceOf( newType, CompositeType.class ) );

		TypeTreeNode newNode = new TypeTreeNode( leaf, newLink );
		parent.addChild( newLink.getUuid(), newNode );

		entityManager.persist( newLink );

	}

	public void moveNodeListener( DropEvent event ) {
		TypeTreeNode source = findNode( (String) event.getDragValue() );
		TypeTreeNode target = findNode( (String) event.getDropValue() );

		if ( source.deepContains( target ) || target.deepContains( source ) )
			return;

		TypeTreeNode sourceParent = current.getTreeRoot().parentOf( source );
		TypeTreeNode targetParent = current.getTreeRoot().parentOf( target );

		sourceParent.removeChild( source.getLink().getUuid() );
		sourceParent.adjustChildrenPriority();

		targetParent.insertAfter( target, source );
		targetParent.adjustChildrenPriority();

		source.getLink().assignSource( typeOf( targetParent ) );
	}

	private TypeTreeNode findNode( String uuid ) {
		TypeTreeNode result = current.getTreeRoot().find( uuid );
		if ( current.getTreeRoot().equals( result ) ) {
			result = (TypeTreeNode) result.getChild( "root" );
		}
		return result;
	}

	public void deleteNode( TypeTreeNode node ) {
		if ( node == null || node.getLink() == null )
			return;

		TypeTreeNode parent = current.getTreeRoot().parentOf( node );
		if ( parent != null ) {

			parent.removeChild( node.getLink().getUuid() );

			TypeLink link = node.getLink();
			Type type = link.getTarget();

			link.delete();
			if ( type != null && type.getAnonymous() ) {
				type.delete();
			}

			closeSelection();

			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );
		}

	}

	public void deleteCurrentNode() {
		if ( currentSelectedNode == null || currentSelectedNode.getLink() == null )
			return;

		TypeTreeNode parent = current.getTreeRoot().parentOf( currentSelectedNode );
		if ( parent != null ) {

			parent.removeChild( currentSelectedNode.getLink().getUuid() );

			TypeLink link = currentSelectedNode.getLink();
			Type type = link.getTarget();

			link.delete();
			if ( type != null && type.getAnonymous() ) {
				type.delete();
			}

			closeSelection();

			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );
		}

	}

	private Type typeOf( TypeTreeNode node ) {
		if ( node.getLink() == null ) {
			return current.getType();
		} else {
			return node.getLink().getTarget();
		}
	}

	private Type createType( String requestedType ) {
		Type result;
		if ( "reference".equals( requestedType ) ) {
			return null;
		} else if ( "textual".equals( requestedType ) ) {
			result = TypeFactory.createTextualType();
		} else if ( "enumerated".equals( requestedType ) ) {
			result = TypeFactory.createEnumeratedType();
		} else if ( "queried".equals( requestedType ) ) {
			result = TypeFactory.createQueriedType();
		} else if ( "quantitative".equals( requestedType ) ) {
			result = TypeFactory.createQuantitativeType();
		} else if ( "temporal".equals( requestedType ) ) {
			result = TypeFactory.createTemporalType();
		} else if ( "composite".equals( requestedType ) ) {
			result = TypeFactory.createCompositeType();
		} else {
			throw new IllegalArgumentException( requestedType + " is not a valid dropType" );
		}
		if ( result != null )
			result.setRecurrent( false );
		return result;
	}

	private void updateTimeRange() {
		TimeRange tr = new TimeRange( new Time( startDate ), new Time( endDate ) );

		if ( current.getType() != null ) {
			current.getType().setTimeRange( tr );
		}
	}

	//
	// navigation methods
	//
	public String save() {
		syncCurrentDefaultValue();
		if ( validateType( current ) ) {
			try {
				utx.begin();
				updateTimeRange();
				conversation.end();
				utx.commit();
				
				message( FacesMessage.SEVERITY_INFO, "Tipo salvato con successo!", true );
			} catch ( RollbackException e ) {
				Throwable t = e.getCause();
				
				while ( ( t != null ) && !( t instanceof ConstraintViolationException ) ) {
					t = t.getCause();
				}
				
				if ( t instanceof ConstraintViolationException ) {
					message( FacesMessage.SEVERITY_ERROR,
							"Impossibile effettuare il salvataggio: nome '" + current.getType().getName()
									+ "' già in uso!",
							true );
				}
			} catch ( Exception e ) {
				message( FacesMessage.SEVERITY_ERROR, "Errore durante il salvataggio!", true );
			}
		} else {
			return null;
		}
		return "save";
	}

	public String cancel() {
		conversation.end();
		return "cancel";
	}

	private void syncCurrentDefaultValue() {
		if ( getSelectedLink() != null ) {
			// if(entityManager.contains(getSelectedLink().getDefaultValue())) {
			// entityManager.remove(getSelectedLink().getDefaultValue());
			// }
			getSelectedLink().syncDefaultValue();
		}
	}

	private Boolean validateType( TypeBean toValidate ) {
		if ( toValidate == null ) {
			return true;
		}

		// check almeno un fenomeno
		if ( toValidate.isEnumerated() ) {
			if ( toValidate.getPhenomena().isEmpty() ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Errore - Inserire almeno un fenomeno!", false );

				return false;

			}

		}

		// check almeno una udm
		else if ( toValidate.isQuantitative() ) {
			if ( toValidate.getUnits().isEmpty() ) {
				message( FacesMessage.SEVERITY_ERROR,
						"Errore - Inserire almeno una unità di misura!", false );
				return false;

			}

		}

		return true;

	}

	public SuggestionInterface getTypeSuggestion() {
		return typeSuggestion;
	}

	public Type getReferencedType() {
		return referencedType;
	}

	public void setReferencedType( Type referencedType ) {
		this.referencedType = referencedType;
	}

	public String getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public boolean getCopy() {
		return Boolean.valueOf( copy );
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate( Date startDate ) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate( Date endDate ) {
		this.endDate = endDate;
	}

	public FactValue getCurrentDefaultValue() {
		return currentDefaultValue;
	}

	public void setCurrentDefaultValue( FactValue currentDefaultValue ) {
		this.currentDefaultValue = currentDefaultValue;
	}
	
	//
	// private methods
	//
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
}
