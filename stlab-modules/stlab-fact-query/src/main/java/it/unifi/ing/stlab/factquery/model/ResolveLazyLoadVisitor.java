package it.unifi.ing.stlab.factquery.model;

import it.unifi.ing.stlab.factquery.model.expression.AndExpression;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.CompositeExpression;
import it.unifi.ing.stlab.factquery.model.expression.ExpressionLink;
import it.unifi.ing.stlab.factquery.model.expression.ExpressionVisitor;
import it.unifi.ing.stlab.factquery.model.expression.OrExpression;

public class ResolveLazyLoadVisitor implements ExpressionVisitor {

	@Override
	public void visitAtomicExpression(AtomicExpression expr) {
		expr.getTypeLink().getName();
	}

	@Override
	public void visitAndExpression(AndExpression expr) {
		visitCompositeType( expr );
		
	}

	@Override
	public void visitOrExpression(OrExpression expr) {
		visitCompositeType( expr );
		
	}
	
	private void visitCompositeType(CompositeExpression expr) {
		for ( ExpressionLink link : expr.listChildren() ) {
			link.getSource();
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
	
}
