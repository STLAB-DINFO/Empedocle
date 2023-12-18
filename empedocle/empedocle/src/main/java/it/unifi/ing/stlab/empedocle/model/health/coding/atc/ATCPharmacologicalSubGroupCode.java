package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "PHARMACOLOGICAL_GROUP" )
public class ATCPharmacologicalSubGroupCode extends ATCCode {
	
	public ATCPharmacologicalSubGroupCode(String uuid) {
		super(uuid);
	}
	protected ATCPharmacologicalSubGroupCode() {
		super();
	}
	
	@Override
	public void setParent(ATCCode parent) {
		if(parent instanceof ATCTherapeuticMainGroupCode) {
			this.parent = parent;
		} else {
			throw new IllegalArgumentException("parent not supported");
		}
	}
}
