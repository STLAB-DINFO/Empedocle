package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "CHEMICAL_GROUP" )
public class ATCChemicalSubGroupCode extends ATCCode {
	
	public ATCChemicalSubGroupCode(String uuid) {
		super(uuid);
	}
	protected ATCChemicalSubGroupCode() {
		super();
	}
	
	@Override
	public void setParent(ATCCode parent) {
		if( parent instanceof ATCPharmacologicalSubGroupCode ) {
			this.parent = parent;
		} else {
			throw new IllegalArgumentException("parent not supported");
		}
	}
}
