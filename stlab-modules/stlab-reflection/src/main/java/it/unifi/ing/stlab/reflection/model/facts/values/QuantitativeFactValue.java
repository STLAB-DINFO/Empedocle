package it.unifi.ing.stlab.reflection.model.facts.values;

import it.unifi.ing.stlab.reflection.model.facts.Quantity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "QT" )
public class QuantitativeFactValue extends FactValue {

	private Quantity quantity;
	
	public QuantitativeFactValue(String uuid) {
		super(uuid);
	}
	protected QuantitativeFactValue() {
		super();
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
		if ( quantity == null || quantity.getValue() == null ) return true;
		return false;
	}
	
	@Override
	public void accept(FactValueVisitor v) {
		v.visitQuantitative(this);
		
	}
	
	@Override
	@Transient
	public String getValue() {
		if (quantity != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(quantity.getValue());
			
			if (quantity.getUnit() != null) {
				sb.append(" ");
				sb.append(quantity.getUnit().getSimbol());
			}
			
			return sb.toString();
			
		}
		else {
			return null;
			
		}
	}
	
}
