package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "CHEMICAL_SUBSTANCE" )
public class ATCChemicalSubstanceCode extends ATCCode {
	
	public ATCChemicalSubstanceCode(String uuid) {
		super(uuid);
	}
	protected ATCChemicalSubstanceCode() {
		super();
	}
	
	@Override
	public void setParent(ATCCode parent) {
		if( parent instanceof ATCChemicalSubGroupCode ) {
			this.parent = parent;
		} else {
			throw new IllegalArgumentException("parent not supported");
		}
	}
}
