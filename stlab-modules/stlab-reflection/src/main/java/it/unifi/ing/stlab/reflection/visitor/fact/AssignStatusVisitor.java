package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class AssignStatusVisitor implements FactVisitor {
	
	private final FactStatus status;
	
	public AssignStatusVisitor(FactStatus status) {
		super();
		this.status = status;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		fact.setStatus(status);

	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		fact.setStatus(status);

	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		fact.setStatus(status);

	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		fact.setStatus(status);
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		fact.setStatus(status);
		
		for ( FactLink link : fact.listActiveLinks() ) {
			link.getTarget().accept( this );
		}

	}

}
