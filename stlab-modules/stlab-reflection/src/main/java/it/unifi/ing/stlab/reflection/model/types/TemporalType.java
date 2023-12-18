package it.unifi.ing.stlab.reflection.model.types;

import it.unifi.ing.stlab.commons.util.TimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue( "DT" )
public class TemporalType extends Type {

	private TimeFormat type;

	public TemporalType(String uuid) {
		super(uuid);
	}
	protected TemporalType() {
		super();
	}

	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visitTemporalType( this );
	}
	
	@Column( name="time_type" )
	@Enumerated( EnumType.STRING )
	public TimeFormat getType() {
		return type;
	}
	public void setType(TimeFormat type) {
		this.type = type;
	}
	
}
