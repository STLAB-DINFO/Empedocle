package it.unifi.ing.stlab.reflection.model.facts;

import it.unifi.ing.stlab.reflection.model.converter.Converter;
import it.unifi.ing.stlab.reflection.model.converter.ConverterRegistry;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Quantity implements Comparable<Quantity> {

	private Double value;
	private Unit unit;

	public Quantity(Double value, Unit measurementUnit ) {
		this.value = value;
		this.unit = measurementUnit;
	}
	public Quantity() {
	}

	@Column( name = "qt_value" )
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	@ManyToOne
	@JoinColumn( name = "qt_unit" )
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit measurementUnit ) {
		this.unit = measurementUnit;
	}
	
	
	//
	// HashCode & Equals
	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Quantity )) return false;
		if ( unit == null || value == null ) return false;
		
		Quantity other = (Quantity) obj;

		return 	unit.equals( other.getUnit() ) &&
				value.equals( other.getValue() );
	}
	
	@Override
	public int compareTo(Quantity qnt) {
		Quantity result = qnt.as(unit);
		
		return value.compareTo(result.value);
	}
	
	public Quantity as(Unit unit) {
		Converter converter = ConverterRegistry.getConverter(this.unit, unit);

		return converter != null ? converter.convert(this) : null;
	}
}