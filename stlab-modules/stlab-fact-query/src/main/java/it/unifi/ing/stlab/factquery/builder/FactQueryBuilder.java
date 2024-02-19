package it.unifi.ing.stlab.factquery.builder;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionLinkFactory;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.factquery.model.expression.CompositeExpression;
import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.reflection.factory.values.FactValueFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class FactQueryBuilder {

	private final FactQuery factQuery;
	private final EntityManager entityManager;
	
	private Expression resultExpr;
	private Expression lastAtomicExpr;
	private Expression lastCompositeExpr;
	
	private FactQueryBuilder(EntityManager entityManager, FactQuery factQuery) {
		this.factQuery = factQuery;
		this.entityManager = entityManager;
		
		initFactQuery();
	}
	
	public static FactQueryBuilder createFactQuery(EntityManager entityManager) {
		return new FactQueryBuilder( entityManager, FactQueryFactory.createQuery() );
		
	}
	
	public FactQueryBuilder selectTxt(String typeLinkName, ComparisonOperator operator, String textValue) {
		TextualFactValue parameter = FactValueFactory.createTextualValue();
		parameter.setText( textValue );
		
		createAtomicExpression( findTypeLink( typeLinkName ), operator, parameter );
		
		return this;
	}
	
	public FactQueryBuilder selectQnt(String typeLinkName, ComparisonOperator operator, Double value) {
		QuantitativeFactValue parameter = FactValueFactory.createQuantitativeValue();
		Quantity qt = new Quantity();
		qt.setValue( value );
		parameter.setQuantity( qt );
		
		createAtomicExpression( findTypeLink( typeLinkName ), operator, parameter );
		
		return this;
	}
	
	public FactQueryBuilder selectQlt(String typeLinkName, ComparisonOperator operator, String phenomenonName) {
		TypeLink typeLink = findTypeLink( typeLinkName );

		QualitativeFactValue parameter = FactValueFactory.createQualitativeValue();
		parameter.setPhenomenon( findPhenomenon( typeLink, phenomenonName ) );
		
		createAtomicExpression( findTypeLink( typeLinkName ), operator, parameter );
		
		return this;
	}
	
	public FactQueryBuilder and() {
		manageCompositeExpression( ExpressionFactory.createAndExpression() );
		return this;
	}
	
	public FactQueryBuilder or() {
		manageCompositeExpression( ExpressionFactory.createOrExpression() );
		return this;
	}
	
	public FactQueryBuilder limit( int limit ) {
		factQuery.setLimit( limit );
		return this;
	}
	
	public FactQueryBuilder offset( int offset ) {
		factQuery.setOffset( offset );
		return this;
	}
	
	public FactQueryBuilder fetch( boolean fetch ) {
		factQuery.setFetch( fetch );
		return this;
	}
	
	public FactQuery get() {
		adjustResult();
		
		if( !canGet() ) throw new RuntimeException( "Operation cannot be performed" );
		
		factQuery.setExpression( resultExpr );
		return factQuery;
	}
	
	//
	// Private Methods
	//
	
	private void adjustResult() {
		if( resultExpr == null )
			resultExpr = lastAtomicExpr;
		
		if( lastCompositeExpr != null && !lastCompositeExpr.listDescendents().contains( lastAtomicExpr ) )
			ExpressionLinkFactory.createExpressionLink( lastCompositeExpr, lastAtomicExpr );
	}
	
	private boolean canGet() {
		if( resultExpr == null || ClassHelper.instanceOf( resultExpr, AtomicExpression.class ) ) return true;
		
		return lastCompositeExpr.listChildren().size() > 1;
	}
	
	private AtomicExpression createAtomicExpression(TypeLink typeLink, ComparisonOperator operator, FactValue parameter) {
		AtomicExpression result = ExpressionFactory.createAtomicExpression();
		result.setTypeLink( typeLink );
		result.setOperator( operator );
		result.setParameter( parameter );
		lastAtomicExpr = result;
		
		return result;
	}
	
	private void manageCompositeExpression(CompositeExpression current) {
		if( lastCompositeExpr != null && current.getClass().equals( lastCompositeExpr.getClass() ) )
			ExpressionLinkFactory.createExpressionLink( lastCompositeExpr, lastAtomicExpr );
		else {
			ExpressionLinkFactory.createExpressionLink( current, lastAtomicExpr );
			if( lastCompositeExpr != null )
				ExpressionLinkFactory.createExpressionLink( lastCompositeExpr, current );
			lastCompositeExpr = current;
			if( resultExpr == null )
				resultExpr = current;
		}
	}
	
	private FactQuery initFactQuery() {
		factQuery.setFetch( false );
		factQuery.setLimit( 1 );
		factQuery.setOffset( 0 );
		
		return factQuery;
		
	}
	
	
	//
	// Find Methods
	//
	
	//FIXME sostiture col metodo findByName del DAO?
	private Phenomenon findPhenomenon( TypeLink typeLink, String phenomenonName ) {
		List<?> results = entityManager.createQuery( " select distinct p " +
										" from QualitativeType t join t.phenomena p " +
										" where t = :type " +
										" and p.name = :name " )
									.setParameter( "type", typeLink.getTarget() )
									.setParameter( "name",  phenomenonName )
									.getResultList();
		
		if ( results == null || results.size() < 1 )
			throw new IllegalArgumentException( "Nessun fenomeno con nome " + phenomenonName + " trovato per l'osservazione "+ typeLink.getTarget() );

		if ( results.size() > 1 )
			throw new IllegalArgumentException( "More than one phenomenon with name " + phenomenonName + " found for observation "+ typeLink.getTarget() );
		
		return (Phenomenon)results.get( 0 );
		
	}
	
	private TypeLink findTypeLink(String name) {
		StringTokenizer tokenizer = new StringTokenizer( name, "." );
		List<TypeLink> partialResults = new LinkedList<TypeLink>();
		
		while(tokenizer.hasMoreElements()) {
			String s = tokenizer.nextToken();
			TypeLink result;

			try {
				result = findTypeLinkByName( s );
				
			} catch( NonUniqueResultException e ) {
				if( partialResults.isEmpty() )
					throw new RuntimeException( e );
				
				result = findTypeLinkByName( partialResults.get( 0 ).getTarget(), s );
				
			}
			
			partialResults.add( 0, result );
		}
		
		return partialResults.get( 0 );
		
	}
	
	private TypeLink findTypeLinkByName(String name) {
		List<?> results = entityManager.createQuery("select distinct t from TypeLink t " +
										"left join fetch t.target " +
										"where t.name = :name" )
									.setParameter( "name", name )
									.getResultList();
						
		if ( results == null || results.size() < 1 )
			throw new IllegalArgumentException( "No subtype with name " + name );

		if ( results.size() > 1 )
			throw new NonUniqueResultException( "More than one subtype with name " + name );
		
		return (TypeLink)results.get( 0 );
	}
	
	private TypeLink findTypeLinkByName(Type type, String name) {
		List<?> results = entityManager.createQuery("select distinct i from CompositeType c left join c.children i " +
										"left join fetch i.target " +
										"where c.id = :type and i.name = :name" )
									.setParameter( "name", name )
									.setParameter( "type", type )
									.getResultList();
						
		if ( results == null || results.size() < 1 )
			throw new IllegalArgumentException( "No subtype with name " + name );

		if ( results.size() > 1 )
			throw new NonUniqueResultException( "More than one subtype with name " + name );
		
		return (TypeLink)results.get( 0 );
	}
	
	
}
