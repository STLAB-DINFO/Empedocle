package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;

public class AssignDefaultValueVisitor implements FactVisitor {

	private final FactValue defaultValue;
	
	public AssignDefaultValueVisitor(FactValue defaultVisitor) {
		super();
		this.defaultValue = defaultVisitor;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		if( defaultValue != null )
			baseVisit( fact );
		else
			fact.setText( null );
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		if( defaultValue != null )
			baseVisit( fact );
		else
			fact.setQuantity( null );
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		if( defaultValue != null )
			baseVisit( fact );
		else
			fact.setPhenomenon( null );
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		if( defaultValue != null )
			baseVisit( fact );
		else
			fact.setDate( null );
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		for ( FactLink link : fact.listActiveLinks() ) {
			AssignDefaultValueVisitor visitor = new AssignDefaultValueVisitor(  link.getType().getDefaultValue() );
			link.getTarget().accept( visitor );
		}
	}
	
	private void baseVisit(Fact fact){
		fact.assignDefaultValue( defaultValue );
	}
	
	
}
