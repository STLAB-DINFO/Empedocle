package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "QT" )
public class QuantitativeFactImpl extends FactImpl implements QuantitativeFact {

	private Quantity quantity;
	
	public QuantitativeFactImpl(String uuid) {
		super(uuid);
	}
	protected QuantitativeFactImpl() {
		super();
	}
	
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, QuantitativeType.class);
    }

	@Embedded
	public Quantity getQuantity() {
		return quantity;
	}
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	@Transient
	@Override
	public boolean isEmpty() {
        return quantity == null || quantity.getValue() == null;
    }

	@Override
	public boolean sameAs(FactImpl obj) {
		if ( getType() == null ) return false;
		if ( !ClassHelper.instanceOf( obj, QuantitativeFactImpl.class )) return false;
		
		QuantitativeFactImpl other = ClassHelper.cast( obj, QuantitativeFactImpl.class );
		
		return getType().equals( other.getType() ) && 
				(( isEmpty() && other.isEmpty() ) || 
				 ( !isEmpty() && getQuantity().equals( other.getQuantity() )));
	}

	@Override
	public QuantitativeFactImpl copy() {
		QuantitativeFactImpl result = FactFactory.createQuantitative();
		result.setType( getType() );
		result.setTimeRange( getTimeRange() );
		result.setContext( getContext() );
		result.setStatus( getStatus() );
		result.setQuantity( getQuantity() );
		return result;
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

}
