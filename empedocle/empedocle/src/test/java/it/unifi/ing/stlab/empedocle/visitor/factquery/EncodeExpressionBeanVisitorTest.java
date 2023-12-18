package it.unifi.ing.stlab.empedocle.visitor.factquery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.empedocle.actions.factquery.ExpressionBean;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionLinkFactory;
import it.unifi.ing.stlab.factquery.model.expression.Expression;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EncodeExpressionBeanVisitorTest {
	
	protected Expression expr;
	
	@Before
	public void setUp() {
		expr = ExpressionFactory.createAndExpression();
		Expression subexpr1 =  ExpressionFactory.createAtomicExpression();
		Expression subexpr2 =  ExpressionFactory.createAtomicExpression();
		
		ExpressionLinkFactory.createExpressionLink( expr, subexpr1 );
		ExpressionLinkFactory.createExpressionLink( expr, subexpr2 );
	}
	
	@Test
	public void testEncode() {
		EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
		expr.accept( visitor );
		
		List<ExpressionBean> result = visitor.getResults();
		
		assertEquals( 2, result.size() );
		assertEquals( expr.listChildrenOrdered().get( 0 ).getTarget(), result.get( 0 ).getExpression() );
		assertEquals( expr.listChildrenOrdered().get( 1 ).getTarget(), result.get( 1 ).getExpression() );
		assertEquals( Operator.and, result.get( 0 ).getOperator() );
		assertNull( result.get( 1 ).getOperator() );
		
	}
	
	@Test
	public void testEncode2() {
		Expression subexpr =  ExpressionFactory.createOrExpression();
		Expression subsubexpr1 =  ExpressionFactory.createAtomicExpression();
		Expression subsubexpr2 =  ExpressionFactory.createAtomicExpression();
		
		ExpressionLinkFactory.createExpressionLink( subexpr, subsubexpr1 );
		ExpressionLinkFactory.createExpressionLink( subexpr, subsubexpr2 );
		ExpressionLinkFactory.createExpressionLink( expr, subexpr );
		
		EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
		expr.accept( visitor );
		
		List<ExpressionBean> result = visitor.getResults();
		
		assertEquals( 4, result.size() );
		assertEquals( expr.listChildrenOrdered().get( 0 ).getTarget(), result.get( 0 ).getExpression() );
		assertEquals( expr.listChildrenOrdered().get( 1 ).getTarget(), result.get( 1 ).getExpression() );
		assertEquals( subsubexpr1, result.get( 2 ).getExpression() );
		assertEquals( subsubexpr2, result.get( 3 ).getExpression() );
		assertEquals( Operator.and, result.get( 0 ).getOperator() );
		assertEquals( Operator.and, result.get( 1 ).getOperator() );
		assertEquals( Operator.or, result.get( 2 ).getOperator() );
		assertNull( result.get( 3 ).getOperator() );
		
	}
	
}
