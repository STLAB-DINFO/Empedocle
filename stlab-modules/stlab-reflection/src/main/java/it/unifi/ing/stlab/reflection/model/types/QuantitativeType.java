package it.unifi.ing.stlab.reflection.model.types;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue( "QT" )
public class QuantitativeType extends Type {

	private Set<UnitUse> units;

	public QuantitativeType(String uuid) {
		super(uuid);
		units = new HashSet<UnitUse>();
	}
	protected QuantitativeType() {
		super();
		units = new HashSet<UnitUse>();
	}

	@OneToMany( mappedBy = "type", cascade = { CascadeType.PERSIST, CascadeType.REMOVE } )
	protected Set<UnitUse> getUnits() {
		return units;
	}
	protected void setUnits(Set<UnitUse> unitaDiMisura) {
		this.units = unitaDiMisura;
	}
	public Set<UnitUse> listUnits() {
		return Collections.unmodifiableSet( units );
	}
	public void addUnit(UnitUse uu) {
		if ( uu == null ) return;
		
		if ( uu.getType() != null ) {
			uu.getType().removeUnit( uu );
		}
		uu.setType( this );
		units.add(uu);
	}
	public void removeUnit(UnitUse uu) {
		if ( !units.contains( uu )) return;
		
		units.remove(uu);
		uu.setType( null );
	}
	public void clearUnits() {
		while ( units.size() > 0 ) {
			removeUnit( units.iterator().next() );
		}
	}
	
	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitQuantitativeType( this );
	}

}
