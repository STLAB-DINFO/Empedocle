package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionLinkFactory;
import it.unifi.ing.stlab.factquery.model.expression.CompositeExpression;
import it.unifi.ing.stlab.factquery.model.expression.Expression;

import java.util.List;

public class DecodeExpressionBean {

	private Expression resultExpr;
	private Expression lastAtomicExpr;
	private Expression lastCompositeExpr;
	
	protected Expression decodeExpressionBean(List<ExpressionBean> exprBeans){
		resultExpr = null;
		lastAtomicExpr = null;
		lastCompositeExpr = null;
		
		for( ExpressionBean expr : exprBeans ) {
			lastAtomicExpr = expr.getExpression();
			createCompositeExpression( expr.getOperator() );
			
		}
		
		adjustResult();
		
		return resultExpr;
	}
	
	private void createCompositeExpression(Operator op) {
		if( op == null ) return;
		
		CompositeExpression current = null;
		switch( op ) {
			case and:
				current = ExpressionFactory.createAndExpression();
				break;
			case or:
				current = ExpressionFactory.createOrExpression();
				break;
			default:
				throw new RuntimeException( "default" );
		}
		
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
	
	private void adjustResult() {
		if( resultExpr == null )
			resultExpr = lastAtomicExpr;
		
		if( lastCompositeExpr != null && !lastCompositeExpr.listDescendents().contains( lastAtomicExpr ) )
			ExpressionLinkFactory.createExpressionLink( lastCompositeExpr, lastAtomicExpr );
	}
	
}
