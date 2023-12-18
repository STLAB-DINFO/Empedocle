package it.unifi.ing.stlab.factquery.model.expression;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CompositeExpression extends Expression {

	public CompositeExpression(String uuid){
		super( uuid );
	}
	protected CompositeExpression(){
		super();
	}
	
}
