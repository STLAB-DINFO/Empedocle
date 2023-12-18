package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "SUB_CATEGORY" )
public class ICD9CMSubCategoryCode extends ICD9CMCode {
	
	public ICD9CMSubCategoryCode(String uuid) {
		super(uuid);
	}
	protected ICD9CMSubCategoryCode() {
		super();
	}
	
	@Override
	public void setParent( ICD9CMCode parent ) {
		if( parent instanceof ICD9CMCategoryCode )
			this.parent = parent;
		else
			throw new IllegalArgumentException("parent not supported");
	}
}
