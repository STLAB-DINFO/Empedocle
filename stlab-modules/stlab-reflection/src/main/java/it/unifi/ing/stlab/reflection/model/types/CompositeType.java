package it.unifi.ing.stlab.reflection.model.types;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "CX" )
public class CompositeType extends Type {

	public CompositeType(String uuid) {
		super(uuid);
	}
	protected CompositeType() {
		super();
	}
	
	
	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitCompositeType( this );
	}

}
