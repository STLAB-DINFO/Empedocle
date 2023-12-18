package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactModifyAction;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class FactGarbageVisitor implements FactVisitor {

	public FactGarbageVisitor() {
		super();
	}

	@Override
	public void visitTextual(TextualFact fact) {
		basicVisit( fact );
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		basicVisit( fact );
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		basicVisit( fact );
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		basicVisit( fact );
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		for( FactLink link : fact.listChildren() ) {
			if( link.getTarget() != null ) {
				link.getTarget().accept( new FactGarbageVisitor() );
			}
			GarbageCollector.getInstance().garbage( link );
		}
		
		basicVisit( fact );
	}
	
	private void basicVisit(Fact fact) {
		GarbageCollector.getInstance().garbage( fact );
		
		FactImpl factImpl = ClassHelper.cast( fact, FactImpl.class );
		
		if( factImpl.getDestination() != null )
			GarbageCollector.getInstance().garbage( factImpl.getDestination() );
		
		if( factImpl.getOrigin() != null ) {
			GarbageCollector.getInstance().garbage( factImpl.getOrigin() );
			
			if( ClassHelper.instanceOf( factImpl.getOrigin(), FactModifyAction.class ) ){
				ClassHelper.cast( factImpl.getOrigin(), FactModifyAction.class ).getSource().accept( new FactGarbageVisitor() );
			}
		}
	}
	
}
