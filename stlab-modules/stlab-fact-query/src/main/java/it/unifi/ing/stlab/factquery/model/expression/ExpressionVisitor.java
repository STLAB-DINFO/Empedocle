package it.unifi.ing.stlab.factquery.model.expression;

public interface ExpressionVisitor {

	void visitAtomicExpression( AtomicExpression expr );
	void visitAndExpression( AndExpression expr );
	void visitOrExpression( OrExpression expr );
	
}
