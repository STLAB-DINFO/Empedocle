package it.unifi.ing.stlab.reflection.model.types;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "QL" )
public abstract class QualitativeType extends Type {


	public QualitativeType(String uuid) {
		super(uuid);
	}

	protected QualitativeType() {
		super();
	}


}
