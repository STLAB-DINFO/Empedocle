package it.unifi.ing.stlab.reflection.lite.visitor.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.commons.random.RandomGenerator;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Test;

public class FactRandomFactoryVisitorTest extends SampleFactFactoryVisitorTest {

	@Override
	public void setUp() {
		RandomGenerator randomGenerator = mock( RandomGenerator.class );
		when( randomGenerator.random( 1, 3 )).thenReturn( 1 );
		when( randomGenerator.random( 2, 5 )).thenReturn( 2 );

		visitor = new FactRandomFactoryVisitor();
		((FactRandomFactoryVisitor)visitor).setRandomGenerator(randomGenerator);
	}

	
	@Test
	@Override
	public void testComposite() throws Exception {
		CompositeType cmp = TypeFactory.createCompositeType();
		TextualType txt1 = TypeFactory.createTextualType();
		TextualType txt2 = TypeFactory.createTextualType();
		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.setPriority(0l);
		TypeLink tl2 = TypeLinkFactory.createLink();
		
		tl1.assignSource(cmp);
		tl1.assignTarget(txt1);
		tl1.setMin(1);
		tl1.setMax(3);
		
		tl2.assignSource(cmp);
		tl2.assignTarget(txt2);
		tl1.setPriority(1l);
		tl2.setMin(2);
		tl2.setMax(5);
		
		cmp.accept(visitor);
		
		CompositeFact osservazione = (CompositeFact)visitor.getFact();
		
		assertTrue( cmp == osservazione.getType() );
		assertEquals( 3, osservazione.listChildren().size() );

	}
}
