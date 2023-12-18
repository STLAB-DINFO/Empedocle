package it.unifi.ing.stlab.factquery.model.expression;

import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue( "ATOM" )
public class AtomicExpression extends Expression {

	private TypeLink typeLink;
	private ComparisonOperator operator;
	private FactValue parameter;
	
	public AtomicExpression(String uuid) {
		super( uuid );
	}
	protected AtomicExpression() {
		super();
	}
	
	
	@ManyToOne
	@JoinColumn( name="type_link_id" )
	public TypeLink getTypeLink() {
		return typeLink;
	}
	public void setTypeLink(TypeLink typeLink) {
		this.typeLink = typeLink;
	}

	
	@Enumerated( EnumType.STRING )
	public ComparisonOperator getOperator() {
		return operator;
	}
	public void setOperator(ComparisonOperator operator) {
		this.operator = operator;
	}
	

	@ManyToOne( fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
	@JoinColumn( name="parameter_id" )
	public FactValue getParameter() {
		return parameter;
	}
	public void setParameter(FactValue parameter) {
		this.parameter = parameter;
	}
	
	public void accept(ExpressionVisitor visitor) {
		visitor.visitAtomicExpression( this ); 
	}
	
}
