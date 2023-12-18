package it.unifi.ing.stlab.factquery.model.expression;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "AND" )
public class AndExpression extends CompositeExpression {

	public AndExpression(String uuid){
		super( uuid );
	}
	protected AndExpression(){
		super();
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visitAndExpression( this ); 
	}
	
}
