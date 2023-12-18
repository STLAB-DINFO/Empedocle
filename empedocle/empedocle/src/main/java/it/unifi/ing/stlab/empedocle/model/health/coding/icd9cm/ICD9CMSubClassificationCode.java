package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "SUB_CLASSIFICATION" )
public class ICD9CMSubClassificationCode extends ICD9CMCode {
	
	public ICD9CMSubClassificationCode(String uuid) {
		super(uuid);
	}
	protected ICD9CMSubClassificationCode() {
		super();
	}
	
	@Override
	public void setParent( ICD9CMCode parent ) {
		if( parent instanceof ICD9CMSubCategoryCode )
			this.parent = parent;
		else
			throw new IllegalArgumentException("parent not supported");
	}
}
