package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.empedocle.visitor.factquery.EncodeExpressionBeanVisitor;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.dao.FactQueryDao;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.dao.types.links.TypeLinkDao;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.*;

@Named( "queryEdit" )
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class FactQueryEdit implements Serializable {
	
	private static final long serialVersionUID = -4794690293328003409L;

	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction utx;
	
	@Inject
	private Conversation conversation;

	@Inject
	private FactQueryDao factQueryDao;
	
	@Inject
	private TypeLinkDao typeLinkDao;
	
	@Inject
	private PhenomenonDao phenomenonDao;
	
	@Inject @HttpParam("queryId")
	private String queryId;
	
	@Inject @HttpParam("returnTo")
	private String returnTo;
	
	@Inject
	private FacesContext context;
	
	private FactQuery current;
	private final List<ExpressionBean> exprBeans;
	private boolean invalidated;
	
	//
	// Public Methods
	//
	
	public FactQueryEdit() {
		exprBeans = new LinkedList<ExpressionBean>();
		invalidated = false;
	}
	
	public List<SelectItem> autocomplete(String suggestion) {
		List<TypeLink> queryResults = typeLinkDao.findBySuggestion( suggestion, 10 );
		
		List<SelectItem> results = new ArrayList<SelectItem>();
		for( TypeLink link : queryResults ){
			results.add( new SelectItem( link.getUuid(), link.getName() ) );
		}
		
		return results;
	}
	
	
	public List<SelectItem> getComparisonOperatorList( TypeLink typeLink ) {
		List<SelectItem> results = new ArrayList<SelectItem>();
		
		if( typeLink != null && 
				( ClassHelper.instanceOf( typeLink.getTarget(), TextualType.class ) ||
				ClassHelper.instanceOf( typeLink.getTarget(), QualitativeType.class ) ) ) {
			
			results.add( new SelectItem( ComparisonOperator.EQUALS, ComparisonOperator.EQUALS.getName() ) );
			results.add( new SelectItem( ComparisonOperator.NOT_EQUALS, ComparisonOperator.NOT_EQUALS.getName() ) );
			results.add( new SelectItem( ComparisonOperator.NULL, ComparisonOperator.NULL.getName() ) );
			results.add( new SelectItem( ComparisonOperator.NOT_NULL, ComparisonOperator.NOT_NULL.getName() ) );
			
		} else {
			for( ComparisonOperator op : ComparisonOperator.values() ) {
				results.add( new SelectItem( op, op.getName() ) );
			}
			
		}
		
		return results;
	}
	
	
	public List<SelectItem> getOperatorList() {
		List<SelectItem> results = new ArrayList<SelectItem>();
		
		for( Operator op : Operator.values() ) {
			results.add( new SelectItem( op, op.getName() ) );
		}
		
		return results;
	}
	
	public List<SelectItem> listPhenomena(QualitativeType type) {
		List<SelectItem> results = new ArrayList<SelectItem>();
		List<Phenomenon> queryResult = phenomenonDao.findByType( type, new Date() );
		
		for( Phenomenon p : queryResult ){
			results.add( new SelectItem( p, p.getName() ) );
		}
		
		return results;
	}
	
	public List<SelectItem> listUnits(QuantitativeType type) {
		List<SelectItem> results = new ArrayList<SelectItem>();
		Set<UnitUse> unitUses = type.listUnits();
		
		for( UnitUse uu : unitUses ){
			results.add( new SelectItem( uu.getUnit(), uu.getUnit().getSimbol() ) );
		}
		
		return results;
	}
	
	public boolean isTextualRendered(TypeLink typeLink, ComparisonOperator operator) {
		return isFactValueEditorRendered( typeLink, operator ) && 
					ClassHelper.instanceOf( typeLink.getTarget(), TextualType.class );
	}
	
	public boolean isTemporalRendered(TypeLink typeLink, ComparisonOperator operator) {
		return isFactValueEditorRendered( typeLink, operator ) && 
				ClassHelper.instanceOf( typeLink.getTarget(), TemporalType.class );
	}
	
	public boolean isQualitativeRendered(TypeLink typeLink, ComparisonOperator operator) {
		return isFactValueEditorRendered( typeLink, operator ) && 
				ClassHelper.instanceOf( typeLink.getTarget(), QualitativeType.class );
	}
	
	public boolean isQuantitativeRendered(TypeLink typeLink, ComparisonOperator operator) {
		return isFactValueEditorRendered( typeLink, operator ) && 
				ClassHelper.instanceOf( typeLink.getTarget(), QuantitativeType.class );
	}
	
	public void operatorChanged(ValueChangeEvent vce) {
		invalidated = true;
		
		if( vce.getOldValue() == null )
			exprBeans.add( new ExpressionBean() );
	}
	
	public boolean isDeleteCriteriaEnabled(){
		return !( queryId != null && !queryId.isEmpty() ) 
					&& exprBeans.size() > 1;
	}
	
	public void removeCriteriaElement(ExpressionBean expr) {
		invalidated = true;
		
		exprBeans.remove( expr );
	}
	
	//FIXME riabilitare funzione?
//	public boolean isUsed() {
//		return queryDao.isUsed( getCurrent() );
//	}
	
	public String close() {
		conversation.end();
		return returnTo != null ? returnTo : "list";
		
	}
	
	public String save() {
		if( !canSave() ){
			context.addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, 
															"Uno o più criteri sono incompleti" , "" ) );
			
			return null;
		}
		
		try {
			utx.begin();

			
			// FIXME da riabilitare -> se stavo modificando e la query non è più valida strutturalmente, la cancello e svuoto il current
//			if( queryId != null && !queryId.isEmpty() && invalidated ) {
//				current.getExpression().delete();
//				current.setExpression( null );
//			}
			
			// se il current non ha un espressione (caso di aggiunta o di modifica invalidante) allora ne creo una nuova
			if( current.getExpression() == null ) {
				Expression newExpr = new DecodeExpressionBean().decodeExpressionBean( exprBeans );
				entityManager.persist( newExpr );
				current.setExpression( newExpr );
			}
			
			// svuoto il garbagecollector e committo la transazione
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );
			
			utx.commit();
			
			conversation.end();
			
			return returnTo != null ? returnTo : "list";
			
		} catch( Exception e ) {
			context.addMessage( null, new FacesMessage( 
											FacesMessage.SEVERITY_ERROR,  
											"An error occurred during saving, unable to save" , "" ) );
			rollback();
			return null;
		}
	}

	
	//
	// Getter Methods
	//
	public FactQuery getCurrent() {
		if( current == null )
			initCurrent();
		
		return current;
	}
	
	public String getQueryId() {
		return queryId;
	}
	
	public List<ExpressionBean> getExprBeans() {
		return exprBeans;
	}

	//
	// Private Methods
	//
	
	private void initCurrent() {
		if( queryId != null && !queryId.isEmpty() ) {
			try {
				utx.begin();
				
				current = factQueryDao.findById( Long.parseLong( queryId ) );
				
				EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
				current.getExpression().accept( visitor );
				exprBeans.addAll( visitor.getResults() );
				
				utx.commit();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		} else {
			current = FactQueryFactory.createQuery();
			current.setLimit( 1 );
			current.setOffset( 0 );
			current.setFetch( false );
			entityManager.persist( current );
			
			exprBeans.add( new ExpressionBean() );
		}
		
	}
	
	private boolean canSave() {
		for( ExpressionBean expr : exprBeans ) {
			expr.syncParameter();
			if( !expr.isValid() ) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isFactValueEditorRendered(TypeLink typeLink, ComparisonOperator operator) {
		return typeLink != null && !( ComparisonOperator.NULL.equals( operator ) || ComparisonOperator.NOT_NULL.equals( operator ) );
	}

	private void rollback() {
		try {
			utx.rollback();
				
		} catch (Exception ute) {
			throw new RuntimeException( ute );
		}
	}
	
}
