package it.unifi.ing.stlab.reflection.impl.visitor.type;

import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QuantitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class SampleFactFactoryVisitor implements TypeVisitor {

	protected FactImpl fact;
	protected FactLinkFactory linkFactory; 

	public SampleFactFactoryVisitor() {
		fact = null;
		linkFactory = new FactLinkFactory();
	}
	
	public FactImpl getFact() {
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
		((QuantitativeFactImpl)fact).setQuantity(new Quantity());
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
					FactInsertLink factLink = (FactInsertLink)linkFactory.insertLink(fact, visitor.getFact());
					factLink.setType(typeLink);
				}
			}
		}
		
	}
	
}