package it.unifi.ing.stlab.reflection.impl.model.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.model.converter.Converter;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;

import org.apache.commons.jexl2.JexlException;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	protected Converter converter;

	@Before
	public void setUp() {
		converter = new Converter(UnitFactory.createUnit(),
				UnitFactory.createUnit(), "100*x");
	}

	@Test
	public void testConvert() {
		Quantity qnt = new Quantity( new Double("1.80"), converter.getUnitFrom());
		Quantity result = converter.convert( qnt );
		
		assertNotNull( result );
		assertEquals( converter.getUnitTo(), result.getUnit());
		assertEquals( new Double("180"), result.getValue());
	}
	
	@Test(expected = JexlException.class)
	public void testConvertIncorrectExpression() {
		converter.setExpression( "100*y" );
		
		Quantity qnt = new Quantity( new Double("1.80"), converter.getUnitFrom());
		converter.convert( qnt );
		
	}	
}
