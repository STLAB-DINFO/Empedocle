package it.unifi.ing.stlab.reflection.lite.visitor.type;

import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.lite.model.facts.FactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.QuantitativeFactLite;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class SampleFactFactoryVisitor implements TypeVisitor {

	protected FactLite fact;
	protected FactLinkFactory linkFactory; 

	public SampleFactFactoryVisitor() {
		fact = null;
		linkFactory = new FactLinkFactory();
	}
	
	public FactLite getFact() {
		return fact;
	}

	@Override
	public void visitTextualType(TextualType tos) {
		fact = FactFactory.createTextual();
		fact.assignType( tos );
	}

	@Override
	public void visitEnumeratedType(EnumeratedType tos) {
		fact = FactFactory.createQualitative();
		fact.assignType( tos );
	}

	@Override
	public void visitQueriedType(QueriedType tos) {
		fact = FactFactory.createQualitative();
		fact.assignType( tos );
	}
	
	@Override
	public void visitQuantitativeType(QuantitativeType tos) {
		fact = FactFactory.createQuantitative();
		((QuantitativeFactLite)fact).setQuantity(new Quantity());
		fact.assignType( tos );
	}
	
	@Override
	public void visitTemporalType(TemporalType tos) {
		fact = FactFactory.createTemporal();
		fact.assignType( tos );
	}

	@Override
	public void visitCompositeType(CompositeType tos) {
		fact = FactFactory.createComposite();
		fact.assignType( tos );
		for ( TypeLink typeLink : tos.listChildrenOrdered() ) {
			if ( typeLink.getMin() != null ) {
				int n = typeLink.getMin().intValue();
				for ( int i = 0; i < n; i ++ ) {
					SampleFactFactoryVisitor visitor = new SampleFactFactoryVisitor();
					if(typeLink.getTarget() != null)
						typeLink.getTarget().accept(visitor);
					FactLink factLink = FactLinkFactory.insertLink(fact, visitor.getFact());
					factLink.setType(typeLink);
				}
			}
		}
		
	}
	
}
