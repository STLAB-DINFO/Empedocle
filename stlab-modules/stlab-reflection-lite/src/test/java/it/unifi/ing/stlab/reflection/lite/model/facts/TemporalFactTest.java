package it.unifi.ing.stlab.reflection.lite.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TemporalFactTest {
	
	protected TemporalFactLite fact;
	
	@Before
	public void setUp() {
		fact = FactFactory.createTemporal();
	}
 
	@Test
	public void testTextualType() {
		fact.setType( TypeFactory.createTextualType() );
		assertNotNull( fact.getType() );
	}

	@Test
	public void testIsEmpty() {
		fact.setDate( null );
		assertTrue( fact.isEmpty() );

		fact.setDate( new Date() );
		assertFalse( fact.isEmpty() );
	}

}
