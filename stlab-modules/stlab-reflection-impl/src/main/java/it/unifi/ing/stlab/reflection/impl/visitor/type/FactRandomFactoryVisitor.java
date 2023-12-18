package it.unifi.ing.stlab.reflection.impl.visitor.type;

import it.unifi.ing.stlab.commons.random.RandomGenerator;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;


public class FactRandomFactoryVisitor extends SampleFactFactoryVisitor {
	
	private RandomGenerator randomGenerator;

	public FactRandomFactoryVisitor() {
		randomGenerator = new RandomGenerator();
	}
	
	public RandomGenerator getRandomGenerator() {
		return randomGenerator;
	}
	public void setRandomGenerator(RandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	@Override
	public void visitCompositeType(CompositeType tos) {
		fact = FactFactory.createComposite();
		fact.assignType( tos );
		
		for (TypeLink typeLink : tos.listChildrenOrdered()) {
			int n = randomGenerator.random( typeLink.getMin(), typeLink.getMax() );
			for ( int i = 0; i < n; i++ ) {
				FactRandomFactoryVisitor visitor = new FactRandomFactoryVisitor();
				if(typeLink.getTarget() != null)
					typeLink.getTarget().accept(visitor);
				FactLink factLink = linkFactory.insertLink(fact, visitor.getFact());
				factLink.setType(typeLink);
			}
		}
	}

	
}