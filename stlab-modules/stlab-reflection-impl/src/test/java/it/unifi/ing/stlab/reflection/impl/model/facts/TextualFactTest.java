package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.types.TextualType;

import org.junit.Before;
import org.junit.Test;

public class TextualFactTest {
	
	protected TextualFactImpl fact;
	
	@Before
	public void setUp() {
		fact = FactFactory.createTextual();
	}
 
	@Test
	public void testTextualType() {
		fact.setType( TypeFactory.createTextualType() );
		assertNotNull( fact.getType() );
	}

	@Test
	public void testIsEmpty() {
		fact.setText( null );
		assertTrue( fact.isEmpty() );

		fact.setText( "   " );
		assertTrue( fact.isEmpty() );

		fact.setText( "hello world" );
		assertFalse( fact.isEmpty() );
	}

	@Test
	public void testSameAs() {
		TextualType type = TypeFactory.createTextualType(); 
		TextualType otherType = TypeFactory.createTextualType();
		
		TextualFactImpl otherObservation = FactFactory.createTextual();
	
	
		fact.setType( type );
		assertTrue( fact.sameAs( fact ));

		fact.setText( "hello1" );
		assertTrue( fact.sameAs( fact ));

		assertFalse( fact.sameAs( otherObservation) );
		
		otherObservation.setType( otherType );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setText( "hello1" );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setType( type );
		assertTrue( fact.sameAs( otherObservation) );

		otherObservation.setText( "hello2" );
		assertFalse( fact.sameAs( otherObservation) );
		
		fact.setText( null );
		assertFalse( fact.sameAs( otherObservation) );
	}
	
	@Test
	public void testCopy() {
		fact.setType( TypeFactory.createTextualType() );
		fact.setText( "hello world" );
		TextualFactImpl copy = fact.copy();
		
		assertTrue( copy.sameAs( fact ));
		assertFalse( copy.equals( fact ));
	}
}
