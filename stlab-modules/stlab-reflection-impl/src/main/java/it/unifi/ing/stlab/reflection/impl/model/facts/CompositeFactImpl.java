package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "CX" )
public class CompositeFactImpl extends FactImpl implements CompositeFact {


	public CompositeFactImpl(String uuid) {
		super(uuid);
	}
	protected CompositeFactImpl() {
		super();
	}

	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
		if ( ClassHelper.instanceOf( type, CompositeType.class )) return true;	
		
		return false;
	}
	
	@Transient
	@Override
	public boolean isEmpty() {
		for( FactLink factLink : listActiveLinks() ){
			if( !(factLink.getTarget().isEmpty()) )
				return false;
		}
		
		return true;
		
	}

	@Override
	public boolean sameAs(FactImpl obj) {
		if ( getType() == null ) return false;
		if ( !ClassHelper.instanceOf( obj, CompositeFactImpl.class )) return false;
		
		CompositeFactImpl other = ClassHelper.cast( obj, CompositeFactImpl.class );
		
		return getType().equals( other.getType());
	}

	@Override
	public CompositeFactImpl copy() {
		CompositeFactImpl result = FactFactory.createComposite();
		result.setType( getType() );
		result.setTimeRange( getTimeRange() );
		result.setContext( getContext() );
		result.setStatus( getStatus() );
		return result;
	}
	
	@Override
	public void assignDefaultValue(FactValue defaultValue) {
		if ( defaultValue != null ) {
			throw new UnsupportedOperationException( "Cannot assign default value to a composite fact");
		}
	}
	
	@Override
	public void accept(FactVisitor visitor) {
		visitor.visitComposite( this );
	}

}
