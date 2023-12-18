package it.unifi.ing.stlab.reflection.lite.converter.visitor;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class UnitFinderVisitor implements TypeVisitor {

	private Unit result;
	private String uuid;
	
	public UnitFinderVisitor(String uuid) {
		super();
		this.uuid = uuid;
	}

	public boolean isFound() {
		return result != null;
	}
	
	public Unit getResult() {
		return result;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
	}

	@Override
	public void visitQueriedType(QueriedType type) {
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		if ( isFound() ) return;
		
		for( UnitUse uu : type.listUnits()) {
			if ( uuid.equals( uu.getUnit().getUuid()))  {
				result = uu.getUnit();
				return;
			}
		}
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
