package it.unifi.ing.stlab.reflection.model.types;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue( "TX" )
public class TextualType extends Type {


	public TextualType(String uuid) {
		super(uuid);
	}
	protected TextualType() {
		super();
	}

	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitTextualType( this );
	}
	
}
