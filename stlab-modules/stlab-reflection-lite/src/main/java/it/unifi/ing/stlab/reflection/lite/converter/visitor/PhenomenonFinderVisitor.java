package it.unifi.ing.stlab.reflection.lite.converter.visitor;

import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class PhenomenonFinderVisitor implements TypeVisitor {

	private Phenomenon result;
	private final String uuid;
	
	public PhenomenonFinderVisitor(String uuid) {
		super();
		this.uuid = uuid;
	}

	public boolean isFound() {
		return result != null;
	}
	
	public Phenomenon getResult() {
		return result;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		if ( isFound() ) return;
		
		for( Phenomenon p : type.listPhenomena()) {
			if ( uuid.equals( p.getUuid()))  {
				result = p;
				return;
			}
		}
	}

	@Override
	public void visitQueriedType(QueriedType type) {
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
	}

	@Override
	public void visitTemporalType(TemporalType type) {
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		if ( isFound() ) return;

		for ( TypeLink tl : type.listChildren() ) {
			tl.getTarget().accept( this );
			if ( isFound() ) {
				return;
			}
		}
	}

}
