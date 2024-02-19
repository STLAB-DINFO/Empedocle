package it.unifi.ing.stlab.reflection.impl.dao;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class InspectFactVisitor implements FactVisitor {

	private final InspectTypeVisitor inspectType;
	
	public InspectFactVisitor() {
		inspectType = new InspectTypeVisitor();
	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		basicInspect( fact );
		
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		fact.getQuantity();
		basicInspect( fact );
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		fact.getPhenomenon();
		basicInspect( fact );
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		basicInspect( fact );
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		basicInspect( fact );
		
	}
	
	private void basicInspect( Fact fact ) {
		FactImpl factImpl = ClassHelper.cast( fact, FactImpl.class );
		fact.getContext();
		fact.getType().accept( inspectType );

		factImpl.getOrigin();
		factImpl.getDestination();
		
		for ( FactLink link : factImpl.listParents() ) {
			ClassHelper.cast( link.getSource(), FactImpl.class ).getStatus();
			ClassHelper.cast( link.getTarget(), FactImpl.class ).getStatus();
		}
		
		for ( FactLink link : fact.listChildren() ) {
			ClassHelper.cast( link.getSource(), FactImpl.class ).getStatus();
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
		
	}
}
