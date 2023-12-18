package it.unifi.ing.stlab.reflection.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.model.converter.Converter;
import it.unifi.ing.stlab.reflection.model.converter.ConverterRegistry;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import org.junit.Before;
import org.junit.Test;

public class QuantityTest {
	
	protected Quantity quantity;
	
	@Before
	public void setUp() {
		quantity = new Quantity();
	}

	@Test
	public void testAs() {
		Unit unitFrom = UnitFactory.createUnit();
		Unit unitTo = UnitFactory.createUnit();
		
		initConverterRegister( unitFrom, unitTo );
		
		quantity.setValue( new Double(1.80) );
		quantity.setUnit( unitFrom );		
		
		Quantity result = quantity.as( unitTo );
		
		assertNotNull(result);
		assertEquals( new Double(180), result.getValue());
		assertEquals( unitTo, result.getUnit());
	}	
	
	@Test
	public void testAsConverterNotFound() {
		Unit unitFrom = UnitFactory.createUnit();
		Unit unitTo = UnitFactory.createUnit();
		
		initConverterRegister( unitFrom, unitTo );
		
		quantity.setValue( new Double(1.80) );
		quantity.setUnit( unitFrom );		
		
		Quantity result = quantity.as( UnitFactory.createUnit() );
		
		assertNull(result);
	}		

	@Test
	public void testCompareTo() {
		Unit u1 = UnitFactory.createUnit();
		Unit u2 = UnitFactory.createUnit();
		
		initConverterRegister( u1, u2 );
		
		quantity.setValue( new Double(1.80) );
		quantity.setUnit( u1 );
		
		Quantity qnt2 = new Quantity();
		qnt2.setValue( new Double(180));
		qnt2.setUnit( u2 );
		
		int result = qnt2.compareTo( quantity );
		
		assertNotNull(result);
		assertEquals(0,result);
		
		quantity.setValue( new Double("1.70") );
		result = qnt2.compareTo( quantity );
		
		assertNotNull(result);
		assertEquals(1,result);
		
		quantity.setValue( new Double("1.90") );
		result = qnt2.compareTo( quantity );
		
		assertNotNull(result);
		assertEquals(-1,result);
		
	}
	
	private void initConverterRegister(Unit unitFrom, Unit unitTo) {
		Converter converter = new Converter( unitFrom, unitTo, "100*x" );
		ConverterRegistry.getConverters().add( converter );		
	}	
}
