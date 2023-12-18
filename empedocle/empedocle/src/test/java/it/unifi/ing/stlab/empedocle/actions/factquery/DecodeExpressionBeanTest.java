package it.unifi.ing.stlab.empedocle.actions.factquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.model.expression.AndExpression;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.factquery.model.expression.OrExpression;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DecodeExpressionBeanTest {

	protected List<ExpressionBean> exprBeans;
	protected DecodeExpressionBean decoder;
	
	@Before
	public void setUp() {
		exprBeans = new LinkedList<ExpressionBean>();
		decoder = new DecodeExpressionBean();
	}
	
	@Test
	public void testDecode1() {
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), null ) );
		
		Expression result = decoder.decodeExpressionBean( exprBeans );
		
		assertNotNull( result );
		assertTrue( result instanceof AtomicExpression );
		assertEquals( 0, result.listChildren().size() );
		assertEquals( exprBeans.get( 0 ).getExpression(), result );
		
	}
	
	@Test
	public void testDecode2() {
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.and ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.and ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), null ) );
		
		Expression result = decoder.decodeExpressionBean( exprBeans );
		
		assertNotNull( result );
		assertTrue( result instanceof AndExpression );
		assertEquals( 3, result.listChildren().size() );
		assertEquals( exprBeans.get( 0 ).getExpression(), result.listChildrenOrdered().get( 0 ).getTarget() );
		assertEquals( exprBeans.get( 1 ).getExpression(), result.listChildrenOrdered().get( 1 ).getTarget() );
		assertEquals( exprBeans.get( 2 ).getExpression(), result.listChildrenOrdered().get( 2 ).getTarget() );
		
	}
	
	@Test
	public void testDecode3() {
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.or ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.or ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.and ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), Operator.or ) );
		exprBeans.add( new ExpressionBean( ExpressionFactory.createAtomicExpression(), null ) );
		
		Expression result = decoder.decodeExpressionBean( exprBeans );
		
		assertNotNull( result );
		assertTrue( result instanceof OrExpression );
		assertEquals( 3, result.listChildren().size() );
		assertEquals( exprBeans.get( 0 ).getExpression(), result.listChildrenOrdered().get( 0 ).getTarget() );
		assertEquals( exprBeans.get( 1 ).getExpression(), result.listChildrenOrdered().get( 1 ).getTarget() );
		
		assertTrue( result.listChildrenOrdered().get( 2 ).getTarget() instanceof AndExpression );
		assertEquals( exprBeans.get( 2 ).getExpression(), result.listChildrenOrdered().get( 2 ).getTarget()
																.listChildrenOrdered().get( 0 ).getTarget() );
		
		assertTrue( result.listChildrenOrdered().get( 2 ).getTarget().listChildrenOrdered().get( 1 ).getTarget() instanceof OrExpression );
		assertEquals( exprBeans.get( 3 ).getExpression(), result.listChildrenOrdered().get( 2 ).getTarget()
																.listChildrenOrdered().get( 1 ).getTarget()
																.listChildrenOrdered().get( 0 ).getTarget() );
		
		assertEquals( exprBeans.get( 4 ).getExpression(), result.listChildrenOrdered().get( 2 ).getTarget()
																.listChildrenOrdered().get( 1 ).getTarget()
																.listChildrenOrdered().get( 1 ).getTarget() );
		
	}
	
}
