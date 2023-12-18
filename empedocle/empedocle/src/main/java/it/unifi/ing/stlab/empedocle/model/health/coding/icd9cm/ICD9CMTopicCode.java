package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "TOPIC" )
public class ICD9CMTopicCode extends ICD9CMCode {

	public ICD9CMTopicCode(String uuid) {
		super(uuid);
	}
	protected ICD9CMTopicCode() {
		super();
	}
	
	@Override
	public void setParent( ICD9CMCode parent ) {
		return;
	}
}
