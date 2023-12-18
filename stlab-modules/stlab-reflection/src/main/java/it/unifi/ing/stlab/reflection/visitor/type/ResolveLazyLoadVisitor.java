package it.unifi.ing.stlab.reflection.visitor.type;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;


public class ResolveLazyLoadVisitor implements TypeVisitor {

	@Override
	public void visitTextualType(TextualType type) {
		type.getName();
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		for ( Phenomenon ph : type.listPhenomena() ) {
			ph.getName();
		}
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		type.getQueryDef();
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		for ( UnitUse uu : type.listUnits()  ) {
			if ( uu.getUnit() != null ) {
				uu.getUnit().getName();
			}
		}
	}
	
	@Override
	public void visitTemporalType(TemporalType type) {
		type.getName();
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		for ( TypeLink link : type.listChildren() ) {
			link.getSource();
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
}
