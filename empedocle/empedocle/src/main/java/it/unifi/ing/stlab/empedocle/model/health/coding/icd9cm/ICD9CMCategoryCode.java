package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "CATEGORY" )
public class ICD9CMCategoryCode extends ICD9CMCode {
	
	public ICD9CMCategoryCode(String uuid) {
		super(uuid);
	}
	protected ICD9CMCategoryCode() {
		super();
	}
	
	@Override
	public void setParent( ICD9CMCode parent ) {
		if( parent instanceof ICD9CMSubTopicCode )
			this.parent = parent;
		else
			throw new IllegalArgumentException("parent not supported");
	}
}
