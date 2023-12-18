package it.unifi.ing.stlab.reflection.model.converter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import org.junit.Test;

public class ConverterRegistryTest {

	@Test
	public void testConverterNotFound() {
		Converter converter = ConverterRegistry.getConverter(
				UnitFactory.createUnit(), UnitFactory.createUnit());

		assertNull(converter);
	}

	@Test
	public void testConverterFound() {
		Unit unitFrom = UnitFactory.createUnit();
		Unit unitTo = UnitFactory.createUnit();
				
		ConverterRegistry.getConverters().add(
				new Converter(unitFrom, unitTo, "100*x"));

		Converter converter = ConverterRegistry.getConverter(unitFrom, unitTo);

		assertNotNull(converter);
	}
}
