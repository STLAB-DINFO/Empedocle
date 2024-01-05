package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TemporalFactValue;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.Date;

public class TemporalFactLite extends FactLite implements TemporalFact {

	private Date date;

	
	//
	// Accessor Methods
	//
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		setType( type );
	}


	//
	// Methods
	//
	
	@Override
	public boolean isEmpty() {
        return date == null;
    }

	@Override
	public void assignDefaultValue(FactValue defaultValue) {
		if ( !ClassHelper.instanceOf(defaultValue, TemporalFactValue.class )) {
			return;
		}
		
		TemporalFactValue dateValue = ClassHelper.cast(defaultValue, TemporalFactValue.class );
		
		if( dateValue.getUseToday() != null && dateValue.getUseToday() )
			setDate( new Date() );
		else
			setDate( dateValue.getDate() );
	}
	
	@Override
	public void accept(FactVisitor visitor) {
		visitor.visitTemporal( this );
	}

	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
        return ClassHelper.instanceOf(type, TemporalType.class);
    }
}
