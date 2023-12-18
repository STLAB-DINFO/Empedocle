package it.unifi.ing.stlab.reflection.lite.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;

import org.junit.Before;
import org.junit.Test;

public class QuantitativeFactTest {
	
	protected QuantitativeFactLite fact;
	
	@Before
	public void setUp() {
		fact = FactFactory.createQuantitative();
	}

	@Test
	public void testQuantitativeType() {
		fact.assignType( TypeFactory.createQuantitativeType() );
		assertNotNull( fact.getType() );
	}

	@Test
	public void testIsEmpty() {
		fact.setQuantity( null );
		assertTrue( fact.isEmpty() );

		fact.setQuantity( new Quantity() );
		fact.getQuantity().setValue( new Double( 123 ) );
		assertFalse( fact.isEmpty() );

		fact.getQuantity().setUnit( UnitFactory.createUnit() );
		assertFalse( fact.isEmpty() );

		fact.getQuantity().setValue( null );
		assertTrue( fact.isEmpty() );
	}

}
