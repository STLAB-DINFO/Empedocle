package it.unifi.ing.stlab.empedocle.visitor.fact.tools;

import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class EmptyFactVisitor implements FactVisitor {

	@Override
	public void visitTextual(TextualFact fact) {
		fact.setText("");

	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		fact.getQuantity().setValue(null);

	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		fact.setPhenomenon(null);
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		fact.setDate(null);

	}

	@Override
	public void visitComposite(CompositeFact fact) {
		for(FactLink lnk : fact.listChildrenOrdered()) {
			lnk.getTarget().accept(this);
			
		}

	}

}
