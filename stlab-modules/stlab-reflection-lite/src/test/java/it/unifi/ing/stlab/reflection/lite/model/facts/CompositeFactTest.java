package it.unifi.ing.stlab.reflection.lite.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;

import org.junit.Before;
import org.junit.Test;

public class CompositeFactTest {
	
	protected CompositeFactLite fact;

	@Before
	public void setUp() {
		fact = FactFactory.createComposite();
	}

	@Test
	public void testCompositeType() {
		fact.assignType( TypeFactory.createCompositeType() );
		assertNotNull( fact.getType() );
	}
	
	@Test
	public void testIsEmpty() {
		TextualFactLite fact1 = FactFactory.createTextual();
		TextualFactLite fact2 = FactFactory.createTextual();
		
		FactLinkFactory factory = new FactLinkFactory();
		FactLinkFactory.insertLink( fact, fact1 );
		FactLinkFactory.insertLink( fact, fact2 );
		
		assertTrue( fact.isEmpty() );
	}
	
	@Test
	public void testIsNotEmpty() {
		TextualFactLite fact1 = FactFactory.createTextual();
		fact1.setText( "ciao" );
		
		FactLinkFactory factory = new FactLinkFactory();
		FactLinkFactory.insertLink( fact, fact1 );
		
		assertFalse( fact.isEmpty() );
	}

}
