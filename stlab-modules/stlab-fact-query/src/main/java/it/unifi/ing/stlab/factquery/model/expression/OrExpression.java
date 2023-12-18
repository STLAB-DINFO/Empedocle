package it.unifi.ing.stlab.factquery.model.expression;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "OR" )
public class OrExpression extends CompositeExpression {

	public OrExpression(String uuid){
		super( uuid );
	}
	protected OrExpression(){
		super();
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visitOrExpression( this ); 
	}
	
}
