package it.unifi.ing.stlab.reflection.impl.dao;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class InspectTypeVisitor  implements TypeVisitor {

	@Override
	public void visitTextualType(TextualType type) {
		baseInspect( type );

	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		type.listPhenomena();
		baseInspect( type );
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		baseInspect( type );
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		type.listUnits();
		baseInspect( type );
	}
	

	@Override
	public void visitTemporalType(TemporalType type) {
		baseInspect( type );
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		baseInspect( type );
	}

	private void baseInspect( Type type ) {
		for ( TypeLink link : type.listParents() ) {
			link.getSource().getName();
			link.getTarget().getName();
		}


		for ( TypeLink link : type.listChildren() ) {
			link.getSource().getName();
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
}