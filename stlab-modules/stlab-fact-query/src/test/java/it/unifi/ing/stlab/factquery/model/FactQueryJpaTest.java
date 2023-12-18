package it.unifi.ing.stlab.factquery.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.factory.FactQueryFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.PersistenceTest;

import java.util.UUID;

import org.junit.Test;

public class FactQueryJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		FactQuery query = FactQueryFactory.createQuery();
		
		query.setName( "name" );
		query.setDescription( "description" );
		query.setFetch( true );
		query.setLimit( 10 );
		query.setOffset( 5 );

		query.setExpression( buildTestExpression() );
		
		entityManager.persist( query );
		
		uuid = query.getUuid();
	}
	
	@Test
	public void testFactQuery(){
		FactQuery query = entityManager.createQuery( " select q " +
														" from FactQuery q " +
														" where q.uuid = :uuid ", FactQuery.class )
													.setParameter("uuid", uuid)
													.getSingleResult();
		
		assertNotNull( query );
		
		assertNotNull( query.getName() );
		assertEquals( "name", query.getName() );
		
		assertNotNull( query.getDescription() );
		assertEquals( "description", query.getDescription() );
		
		assertNotNull( query.getFetch() );
		assertEquals( Boolean.TRUE, query.getFetch() );
		
		assertNotNull( query.getLimit() );
		assertEquals( new Integer( 10 ), query.getLimit() );
		
		assertNotNull( query.getOffset() );
		assertEquals( new Integer( 5 ), query.getOffset() );

		AtomicExpression expr = ClassHelper.cast( query.getExpression(), AtomicExpression.class );
		assertNotNull( expr );
		assertEquals( "link", expr.getTypeLink().getName() );
		assertEquals( ComparisonOperator.EQUALS, expr.getOperator() );
		assertEquals( "testo", expr.getParameter().getValue() );
		
	}
	
	
	@Test
	public void testCascadeDelete(){
		FactQuery query = entityManager.createQuery( " select q " +
														" from FactQuery q " +
														" where q.uuid = :uuid ", FactQuery.class )
													.setParameter("uuid", uuid)
													.getSingleResult();
		
		Long exprId = query.getExpression().getId();
		Long paramId = ClassHelper.cast( query.getExpression(), AtomicExpression.class ).getParameter().getId();
		
		entityManager.remove( query );
		
		assertNull( entityManager.find( Expression.class, exprId ) );
		assertNull( entityManager.find( FactQuery.class, paramId ) );
		
	}
	
	
	private Expression buildTestExpression() {
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.setName( "link" );
		entityManager.persist( typeLink );
		
		TextualFactValue factValue = new TextualFactValue( UUID.randomUUID().toString() );
		factValue.setText( "testo" );
		
		AtomicExpression expression = ExpressionFactory.createAtomicExpression();
		expression.setTypeLink( typeLink );
		expression.setOperator( ComparisonOperator.EQUALS );
		expression.setParameter( factValue );
		
		return expression;
	}
	
}
