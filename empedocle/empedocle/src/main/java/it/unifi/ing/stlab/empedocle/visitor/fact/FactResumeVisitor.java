package it.unifi.ing.stlab.empedocle.visitor.fact;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.view.factory.TypeSelectorFactory;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

/**
 * Visitor che recupera i dati dei Fact da visite precedenti.
 * Assume di lavorare con Fact ricorrenti o discendenti di ricorrenti
 * (questo significa che serve un prefiltraggio in tal senso prima di invocare il visitor)
 *
 */
public class FactResumeVisitor implements FactVisitor {
	
	private Fact destination;
	private TypeSelector currentSelector;
	
	public FactResumeVisitor(Fact destination) {
		super();
		this.destination = destination;
		currentSelector = TypeSelectorFactory.createSelector();
	}

	public FactResumeVisitor(Fact destination, TypeSelector initialPath) {
		super();
		this.destination = destination;
		currentSelector = initialPath;

	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		if(currentSelector.getTypeLink() == null) {
			ClassHelper.cast(destination, TextualFact.class).setText(fact.getText());
		}
		else {
		
			Fact target = getCurrentTarget();
	
			if(ClassHelper.instanceOf(target, TextualFact.class)) {
				ClassHelper.cast(target, TextualFact.class).setText(fact.getText());
			}
		
		}
		
	}
	
	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		if(currentSelector.getTypeLink() == null) {
			ClassHelper.cast(destination, QuantitativeFact.class).setQuantity(fact.getQuantity());
		}
		else {
			Fact target = getCurrentTarget();
	
			if(ClassHelper.instanceOf(target, QuantitativeFact.class)) {
				ClassHelper.cast(target, QuantitativeFact.class).setQuantity(fact.getQuantity());
			}
		}

	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		if(currentSelector.getTypeLink() == null) {
			ClassHelper.cast(destination, QualitativeFact.class).setPhenomenon(fact.getPhenomenon());
		}
		else {
			Fact target = getCurrentTarget();
			
			if(ClassHelper.instanceOf(target, QualitativeFact.class)) {
				ClassHelper.cast(target, QualitativeFact.class).setPhenomenon(fact.getPhenomenon());
			}
		}
		
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		if(currentSelector.getTypeLink() == null) {
			ClassHelper.cast(destination, TemporalFact.class).setDate(fact.getDate());
		}
		else {
		
			Fact target = getCurrentTarget();
	
			if(ClassHelper.instanceOf(target, TextualFact.class)) {
				ClassHelper.cast(target, TemporalFact.class).setDate(fact.getDate());
			}
		
		}
		
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		
		for(FactLink child : fact.listActiveLinks()) {
			
			if(currentSelector.getTypeLink() == null) {
				currentSelector.setTypeLink(child.getType());
				
			}
			else {
				TypeSelector nextSelector = TypeSelectorFactory.createSelector();
				nextSelector.setTypeLink(child.getType());
				TypeSelector last = currentSelector.getLast();
				last.assignNext(nextSelector);
			}
			
			FactResumeVisitor v = new FactResumeVisitor(destination, currentSelector);
			child.getTarget().accept(v);
			
			if(currentSelector.getNext() == null) {
				currentSelector.setTypeLink(null);
			}
			else {
				TypeSelector penultimate = currentSelector;
				TypeSelector target = currentSelector.getNext();
				while(target.getNext() != null) {
					penultimate = target;
					target = target.getNext();
				}
				
				penultimate.unlinkNext();
			}

		}
		
	}

	private Fact getCurrentTarget() {
		return currentSelector.apply(destination);
	}

}
