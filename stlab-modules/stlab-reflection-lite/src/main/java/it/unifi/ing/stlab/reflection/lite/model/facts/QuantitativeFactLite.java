package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

public class QuantitativeFactLite extends FactLite implements QuantitativeFact {

	private Quantity quantity;
	
	
	//
	// Accessor Methods
	//
	
	public Quantity getQuantity() {
		return quantity;
	}
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	
	//
	// Methods
	//

	@Override
	public boolean isEmpty() {
		if ( quantity == null || quantity.getValue() == null ) return true;

		return false;
	}

	@Override
	public void assignDefaultValue(FactValue defaultValue) {
		if ( !ClassHelper.instanceOf(defaultValue, QuantitativeFactValue.class )) {
			return;
		}
		
		QuantitativeFactValue quantityValue = ClassHelper.cast(defaultValue, QuantitativeFactValue.class );
		setQuantity( quantityValue.getQuantity() );
	}
	
	@Override
	public void accept(FactVisitor visitor) {
		visitor.visitQuantitative( this );
	}

	
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
		if ( ClassHelper.instanceOf( type, QuantitativeType.class )) return true;
		
		return false;
	}
}
