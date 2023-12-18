package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class RecursiveResolveLazyLoadVisitor implements FactVisitor {

	@Override
	public void visitTextual(TextualFact fact) {
		basicVisit(fact);

	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		basicVisit(fact);

	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		basicVisit(fact);

	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		basicVisit(fact);

	}

	@Override
	public void visitComposite(CompositeFact fact) {
		basicVisit(fact);

	}
	
	private void basicVisit(Fact f) {
		FactImpl factImpl = ClassHelper.cast( f, FactImpl.class );
		
		for(Fact fb : factImpl.listBefore()) {
			for(FactLink fl : fb.listChildren()) {
				// NB: i removeLink non hanno target
				if(fl.getTarget() != null) {
					fl.getTarget().accept(this);
				}
			}
		}
	}

}
