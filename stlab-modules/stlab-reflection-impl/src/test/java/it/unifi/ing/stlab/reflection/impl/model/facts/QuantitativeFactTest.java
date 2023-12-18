package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import org.junit.Before;
import org.junit.Test;

public class QuantitativeFactTest {
	
	protected QuantitativeFactImpl fact;
	
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

	@Test
	public void testSameAs() {
		QuantitativeType type = TypeFactory.createQuantitativeType(); 
		QuantitativeType otherType = TypeFactory.createQuantitativeType();
		QuantitativeFactImpl otherObservation = FactFactory.createQuantitative();
		Unit measurementUnit = UnitFactory.createUnit();
	
		fact.setType( type );
		assertTrue( fact.sameAs( fact ));

		fact.setQuantity( new Quantity( new Double( 123 ), measurementUnit ));
		assertTrue( fact.sameAs( fact ));

		assertFalse( fact.sameAs( otherObservation) );
		
		otherObservation.setType( otherType );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setQuantity( new Quantity( new Double( 123 ), measurementUnit ));
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setType( type );
		assertTrue( fact.sameAs( otherObservation) );

		otherObservation.setQuantity( new Quantity( new Double( 456 ), measurementUnit ));
		assertFalse( fact.sameAs( otherObservation) );
	}

	@Test
	public void testCopy() {
		fact.assignType( TypeFactory.createQuantitativeType() );
		fact.setQuantity( new Quantity( new Double( 123 ), UnitFactory.createUnit() ) );
		QuantitativeFactImpl copy = fact.copy();
		
		assertTrue( copy.sameAs( fact ));
		assertFalse( copy.equals( fact ));
	}
}
