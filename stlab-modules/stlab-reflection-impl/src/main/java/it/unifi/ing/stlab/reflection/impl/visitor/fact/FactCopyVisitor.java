package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class FactCopyVisitor implements FactVisitor {
	
	private FactImpl result;

	public FactImpl getResult() {
		return result;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		result = FactFactory.createTextual();
		result.assignType(fact.getType());
		((TextualFact) result).setText(fact.getText());
		
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		result = FactFactory.createQuantitative();
		result.assignType(fact.getType());
		Quantity q = new Quantity();
		if(fact.getQuantity() != null) {
			q.setUnit(fact.getQuantity().getUnit());
			q.setValue(fact.getQuantity().getValue());
			
		}
		
		((QuantitativeFact) result).setQuantity(q);
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		result = FactFactory.createQualitative();
		result.assignType(fact.getType());
		((QualitativeFact) result).setPhenomenon(fact.getPhenomenon());
		
	}

	@Override
	public void visitTemporal(TemporalFact fact) {
		result = FactFactory.createTemporal();
		result.assignType(fact.getType());
		((TemporalFact) result).setDate(fact.getDate());
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		CompositeFact composite = ClassHelper.cast( fact, CompositeFact.class );
		result = FactFactory.createComposite();
		result.assignType(fact.getType());
		
		for(FactLink lnk : composite.listChildrenOrdered()) {
			FactCopyVisitor v = new FactCopyVisitor();
			lnk.getTarget().accept(v);
			
			FactLinkFactory ff = new FactLinkFactory();
			FactInsertLink fl = (FactInsertLink) ff.insertLink(result, v.getResult());
			fl.setType(lnk.getType());
			
		}
		
	}
	
}
