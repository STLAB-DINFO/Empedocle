package it.unifi.ing.stlab.factquery.factory.expression;

import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.factquery.model.expression.ExpressionLink;

import java.util.UUID;

public class ExpressionLinkFactory {

	public static ExpressionLink createExpressionLink() {
		return new ExpressionLink( UUID.randomUUID().toString() );
		
	}
	
	public static ExpressionLink createExpressionLink(Expression source, Expression target) {
		ExpressionLink result = new ExpressionLink( UUID.randomUUID().toString() );
		result.setPriority( new Long( source.listChildren().size() ) );
		result.assignSource(source);
		result.assignTarget(target);
		
		return result;
		
	}
	
}
