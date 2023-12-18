package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "ANATOMICAL_GROUP" )
public class ATCAnatomicalMainGroupCode extends ATCCode {

	public ATCAnatomicalMainGroupCode(String uuid) {
		super(uuid);
	}
	protected ATCAnatomicalMainGroupCode() {
		super();
	}
	
	@Override
	public void setParent(ATCCode parent) {
		return;
	}
}
