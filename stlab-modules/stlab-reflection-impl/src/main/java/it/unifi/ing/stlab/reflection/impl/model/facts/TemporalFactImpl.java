package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TemporalFactValue;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "DT" )
public class TemporalFactImpl extends FactImpl implements TemporalFact {

	private Date date;
	
	public TemporalFactImpl(String uuid) {
		super(uuid);
	}
	protected TemporalFactImpl() {
		super();
	}

	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		setType( type );
	}

	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
		if ( ClassHelper.instanceOf( type, TemporalType.class )) return true;
		
		return false;
	}
	
	@Basic
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
	//
	// Methods
	//
	@Transient
	@Override
	public boolean isEmpty() {
		if ( date == null )
			return true;
		
		return false;
	}

	@Override
	public boolean sameAs(FactImpl obj) {
		if ( getType() == null ) return false;
		if ( !ClassHelper.instanceOf(obj, TemporalFactImpl.class )) return false;
		
		TemporalFactImpl other = ClassHelper.cast( obj, TemporalFactImpl.class );
		
		return getType().equals( other.getType() ) && 
				(( isEmpty() && other.isEmpty() ) || 
					( !isEmpty() && getDate().equals( other.getDate() )));
	}

	@Override
	public TemporalFactImpl copy() {
		TemporalFactImpl result = FactFactory.createTemporal();
		result.setType( getType() );
		result.setTimeRange( getTimeRange() );
		result.setContext( getContext() );
		result.setStatus( getStatus() );
		result.setDate( getDate() );
		return result;
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
}
