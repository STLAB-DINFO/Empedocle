package it.unifi.ing.stlab.reflection.lite.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;

import org.junit.Before;
import org.junit.Test;

public class TextualFactTest {
	
protected TextualFactLite fact;
	
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

}
