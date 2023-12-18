package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.factory.expression.ExpressionFactory;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.ComparisonOperator;
import it.unifi.ing.stlab.reflection.factory.values.FactValueFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

public class ExpressionBean {

	private AtomicExpression expression;
	private Operator operator;
	
	public ExpressionBean() {
		expression = ExpressionFactory.createAtomicExpression();
	}
	public ExpressionBean(AtomicExpression expression, Operator operator) {
		this.expression = expression;
		this.operator = operator;
	}
	
	
	public AtomicExpression getExpression() {
		return expression;
	}
	public void setExpression(AtomicExpression expression) {
		this.expression = expression;
	}
	
	
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public void resetParameter(){
		// se è stato impostato un nuovo typeLink, cancello il vecchio parametro e ne metto uno nuovo del giusto tipo 
		if( expression.getTypeLink() != null) {
			GarbageCollector.getInstance().garbage( expression.getParameter() );
			expression.setParameter( createParameter( expression.getTypeLink().getTarget() ) );
		}
		
	}
	protected void syncParameter(){
		// se l'operatore non prevede l'uso di un parametro, lo cancello prima di salvare
		if( ( ComparisonOperator.NULL.equals( expression.getOperator() ) || 
				ComparisonOperator.NOT_NULL.equals( expression.getOperator() ) ) ){
			
			GarbageCollector.getInstance().garbage( expression.getParameter() );
			expression.setParameter( null );
		}
	}
	
	public boolean isValid(){
		if( expression.getTypeLink() == null )
			return false;
		
		if( expression.getOperator() == null )
			return false;
		
		if ( !( ComparisonOperator.NULL.equals( expression.getOperator() ) || 
				ComparisonOperator.NOT_NULL.equals( expression.getOperator() ) ) && 
				expression.getParameter().isEmpty() ) {
			return false;
		}
		
		return true;
	}
	
	
	private FactValue createParameter(Type type) {
		if( ClassHelper.instanceOf( type, TextualType.class ) )
			return FactValueFactory.createTextualValue();
		
		else if( ClassHelper.instanceOf( type, QualitativeType.class ) )
			return FactValueFactory.createQualitativeValue();
		
		else if( ClassHelper.instanceOf( type, QuantitativeType.class ) ) {
			QuantitativeFactValue result = FactValueFactory.createQuantitativeValue();
			result.setQuantity(new Quantity());
			return result;
		}
		
		else if( ClassHelper.instanceOf( type, TemporalType.class ) )
			return FactValueFactory.createTemporalValue();
		
		return null;
	}
	
}
