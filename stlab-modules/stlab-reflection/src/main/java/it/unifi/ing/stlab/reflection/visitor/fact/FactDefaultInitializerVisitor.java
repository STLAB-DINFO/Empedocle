package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.Iterator;

public class FactDefaultInitializerVisitor implements FactVisitor {

	public FactDefaultInitializerVisitor() {
		super();
	}

	@Override
	public void visitTextual(TextualFact fact) {
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		if ( fact.getQuantity() != null ) 
			return;
		
		Quantity q = new Quantity();
		QuantitativeType type = ClassHelper.cast(fact.getType(), QuantitativeType.class);
		
		Iterator<UnitUse> it = type.listUnits().iterator();
		q.setUnit( it.next().getUnit() );
		
		fact.setQuantity(q);
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		for ( FactLink link : fact.listChildren() ) {
			link.getTarget().accept( this );
		}
	}
	
	
}
