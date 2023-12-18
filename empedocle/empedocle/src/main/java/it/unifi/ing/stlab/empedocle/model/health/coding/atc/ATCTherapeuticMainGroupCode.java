package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "THERAPEUTIC_GROUP" )
public class ATCTherapeuticMainGroupCode extends ATCCode {
	
	public ATCTherapeuticMainGroupCode(String uuid) {
		super(uuid);
	}
	protected ATCTherapeuticMainGroupCode() {
		super();
	}
	
	@Override
	public void setParent(ATCCode parent) {
		if(parent instanceof ATCAnatomicalMainGroupCode) {
			this.parent = parent;
		} else {
			throw new IllegalArgumentException("parent not supported");
		}
	}
}
