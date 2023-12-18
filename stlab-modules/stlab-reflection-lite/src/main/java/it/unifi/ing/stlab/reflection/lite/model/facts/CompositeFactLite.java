package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.Type;

public class CompositeFactLite extends FactLite implements CompositeFact {

	//
	// Accessor Methods
	//
	
	@Override
	public void assignType(Type type) {
		if ( !isValidType( type ))
			throw new IllegalArgumentException();
		
		super.setType(type);
	}
	
	
	//
	// Methods
	//
	
	@Override
	public boolean isEmpty() {
		for( FactLink factLink : listActiveLinks() ){
			if( !(factLink.getTarget().isEmpty()) )
				return false;
		}
		
		return true;
		
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

	private boolean isValidType( Type type ) {
		if ( type == null ) return true;
		if ( ClassHelper.instanceOf( type, CompositeType.class )) return true;	
		
		return false;
	}
	
}
