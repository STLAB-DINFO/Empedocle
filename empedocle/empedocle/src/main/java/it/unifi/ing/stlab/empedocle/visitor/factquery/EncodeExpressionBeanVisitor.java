package it.unifi.ing.stlab.empedocle.visitor.factquery;

import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.empedocle.actions.factquery.ExpressionBean;
import it.unifi.ing.stlab.factquery.model.expression.*;

import java.util.LinkedList;
import java.util.List;

public class EncodeExpressionBeanVisitor implements ExpressionVisitor {

	private final List<ExpressionBean> results;
	private Operator op;
	
	public EncodeExpressionBeanVisitor() {
		results = new LinkedList<ExpressionBean>();
	}
	
	@Override
	public void visitAtomicExpression(AtomicExpression expr) {
		ExpressionBean result = new ExpressionBean();
		result.setExpression( expr );
		if( op != null )
			result.setOperator( op );
		
		results.add( result );
		
	}

	@Override
	public void visitAndExpression(AndExpression expr) {
		for( ExpressionLink link : expr.listChildrenOrdered() ){
			EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
			if( expr.listChildrenOrdered().indexOf( link ) < expr.listChildrenOrdered().size() - 1 )
				visitor.setOp( Operator.and );
			link.getTarget().accept( visitor );
			results.addAll( visitor.getResults() );
		}
		
	}

	@Override
	public void visitOrExpression(OrExpression expr) {
		for( ExpressionLink link : expr.listChildrenOrdered() ){
			EncodeExpressionBeanVisitor visitor = new EncodeExpressionBeanVisitor();
			if( expr.listChildrenOrdered().indexOf( link ) < expr.listChildrenOrdered().size() - 1 )
				visitor.setOp( Operator.or );
			link.getTarget().accept( visitor );
			results.addAll( visitor.getResults() );
		}
		
	}

	
	public List<ExpressionBean> getResults() {
		return results;
	}

	
	public Operator getOp() {
		return op;
	}
	public void setOp(Operator op) {
		this.op = op;
	}
	
}
