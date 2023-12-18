package it.unifi.ing.stlab.reflection.lite.converter.visitor;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class TypeFinderVisitor implements TypeVisitor {

	private Type result;
	private String uuid;
	
	public TypeFinderVisitor(String uuid) {
		super();
		this.uuid = uuid;
	}

	public boolean isFound() {
		return result != null;
	}
	
	public Type getResult() {
		return result;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
		checkType( type );
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		checkType( type );
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		checkType( type );
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		checkType( type );
	}

	@Override
	public void visitTemporalType(TemporalType type) {
		checkType( type );
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		checkType( type );
		if ( isFound() ) return;

		for ( TypeLink tl : type.listChildren() ) {
			tl.getTarget().accept( this );
			if ( isFound() ) {
				return;
			}
		}
	}

	private void checkType( Type type ) {
		if ( isFound() ) return;
		
		if ( uuid.equals( type.getUuid() )) {
			result = type;
		}
	}
}
