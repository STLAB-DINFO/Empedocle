package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class AssignContextVisitor implements FactVisitor {

	private FactContext factContext;

	public AssignContextVisitor(FactContext factContext) {
		super();
		this.factContext = factContext;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		fact.setContext( factContext );
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		fact.setContext( factContext );
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		fact.setContext( factContext );
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		fact.setContext( factContext );
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		fact.setContext( factContext );

		for ( FactLink link : fact.listChildren() ) {
			link.getTarget().accept( this );
		}
	}
}
