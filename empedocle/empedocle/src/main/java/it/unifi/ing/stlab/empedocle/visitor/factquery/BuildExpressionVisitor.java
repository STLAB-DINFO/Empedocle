package it.unifi.ing.stlab.empedocle.visitor.factquery;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.factquery.model.expression.*;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.HashMap;
import java.util.Map;

public class BuildExpressionVisitor implements ExpressionVisitor {

	private final StringBuffer buffer;
	private final Map<String, Object> additionalParams;
	private int index;
	
	public BuildExpressionVisitor() {
		buffer = new StringBuffer();
		additionalParams = new HashMap<String, Object>();
		index = 0;
	}
	
	@Override
	public void visitAtomicExpression(AtomicExpression expr) {
		String joinName = "fl"+index;
		
		buffer.append( " exists ( select " )
				.append( joinName )
				.append( ".target from FactLinkImpl " )
				.append( joinName )
				.append( " where " )
				.append( joinName )
				.append( ".type =" )
				.append( " :type" )
				.append( index )
				.append( " and " )
				.append( joinName );
		
		additionalParams.put( "type"+index, expr.getTypeLink() );
		
		writeTarget( expr.getTypeLink().getTarget() );
		
		buffer.append( expr.getOperator().getSymbol() );
		
		writeParameter( expr.getParameter() );
		
		buffer.append( " and " )
				.append( joinName )
				.append( ".target.destination is null" )
				.append( " and " )
				.append( joinName )
				.append( ".target.status != :notStatus" )
				.append( " and " )
				.append( joinName )
				.append( ".target.context = factroot.context ")
				.append( ") " ); 
		
		index++;
	}

	@Override
	public void visitAndExpression(AndExpression expr) {
		boolean first = true;
		for( ExpressionLink e : expr.listChildrenOrdered() ) {
			if( !first )
				buffer.append( " and " );
			
			e.getTarget().accept( this );
			first = false;
		}
		
	}

	@Override
	public void visitOrExpression(OrExpression expr) {
		boolean first = true;
		for( ExpressionLink e : expr.listChildrenOrdered() ) {
			if( !first )
				buffer.append( " or " );
			
			e.getTarget().accept( this );
			first = false;
		}
		
	}

	public String getResult() {
		return buffer.toString();
	}
	
	public Map<String, Object> getAdditionalParams() {
		return additionalParams;
	}

	private void writeTarget( Type type ) {
		if( ClassHelper.instanceOf( type, TextualType.class ) ) {
			buffer.append( ".target.text " );

		} else if( ClassHelper.instanceOf( type, QuantitativeType.class ) ) {
			buffer.append( ".target.quantity.value " );
			
		} else if( ClassHelper.instanceOf( type, QualitativeType.class ) ) {
			buffer.append( ".target.phenomenon " );
			
		} else {
			throw new RuntimeException( "errore nel determinare il tipo del parametro da usare" );
			
		}

	}
	
	private void writeParameter(FactValue parameter) {
		if( parameter == null )
			return;
		
		if( ClassHelper.instanceOf( parameter, TextualFactValue.class ) ) {
			additionalParams.put( "value"+index, ( ClassHelper.cast( parameter, TextualFactValue.class ).getText() ) );

		} else if( ClassHelper.instanceOf( parameter, QuantitativeFactValue.class ) ) {
			additionalParams.put( "value"+index, ( ClassHelper.cast( parameter, QuantitativeFactValue.class ).getQuantity().getValue() ) );
			
		} else if( ClassHelper.instanceOf( parameter, QualitativeFactValue.class ) ) {
			additionalParams.put( "value"+index, ( ClassHelper.cast( parameter, QualitativeFactValue.class ).getPhenomenon() ) );
			
		} else {
			throw new RuntimeException( "errore nel determinare il parametro da usare" );
			
		}
		
		buffer.append( " :value" )
				.append( index );
			
	}
	
}
