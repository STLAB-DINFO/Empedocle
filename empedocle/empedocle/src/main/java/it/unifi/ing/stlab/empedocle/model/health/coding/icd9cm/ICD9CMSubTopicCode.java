package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "SUB_TOPIC" )
public class ICD9CMSubTopicCode extends ICD9CMCode {
	
	public ICD9CMSubTopicCode(String uuid) {
		super(uuid);
	}
	protected ICD9CMSubTopicCode() {
		super();
	}
	
	@Override
	public void setParent(ICD9CMCode parent) {
		if( parent instanceof ICD9CMTopicCode )
			this.parent = parent;
		else
			throw new IllegalArgumentException("parent not supported");
	}
}
