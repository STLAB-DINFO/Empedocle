package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;

import org.junit.Before;
import org.junit.Test;

public class CompositeFactTest {
	
	protected CompositeFactImpl fact;

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
		TextualFactImpl fact1 = FactFactory.createTextual();
		TextualFactImpl fact2 = FactFactory.createTextual();
		
		FactLinkFactory factory = new FactLinkFactory();
		factory.insertLink( fact, fact1 );
		factory.insertLink( fact, fact2 );
		
		assertTrue( fact.isEmpty() );
	}
	
	@Test
	public void testIsNotEmpty() {
		TextualFactImpl fact1 = FactFactory.createTextual();
		fact1.setText( "ciao" );
		
		FactLinkFactory factory = new FactLinkFactory();
		factory.insertLink( fact, fact1 );
		
		assertFalse( fact.isEmpty() );
	}

	@Test
	public void testSameAs() {
		CompositeType type = TypeFactory.createCompositeType(); 
		CompositeType otherType = TypeFactory.createCompositeType();
		
		CompositeFactImpl otherObservation = FactFactory.createComposite();
	
	
		fact.setType( type );
		assertTrue( fact.sameAs( fact ));
		assertFalse( fact.sameAs( otherObservation) );
		
		otherObservation.setType( otherType );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setType( type );
		assertTrue( fact.sameAs( otherObservation) );
	}

	@Test
	public void testCopy() {
		fact.setType( TypeFactory.createCompositeType() );
		CompositeFactImpl copy = fact.copy();
		
		assertTrue( copy.sameAs( fact ));
		assertFalse( copy.equals( fact ));
	}
}
