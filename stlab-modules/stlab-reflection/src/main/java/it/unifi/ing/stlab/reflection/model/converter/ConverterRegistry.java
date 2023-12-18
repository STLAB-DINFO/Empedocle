package it.unifi.ing.stlab.reflection.model.converter;

import it.unifi.ing.stlab.reflection.model.types.Unit;

import java.util.HashSet;
import java.util.Set;

public class ConverterRegistry {
	
	private static Set<Converter> converters = new HashSet<Converter>();
	
	public static Converter getConverter( Unit from, Unit to ) {
		for( Converter converter : converters ) {
			if( converter.getUnitFrom().equals(from) && converter.getUnitTo().equals(to) ) {
				return converter;
			}
		}
		return null;
	}

	public static Set<Converter> getConverters() {
		return converters;
	}
	public static void setConverters(Set<Converter> converters) {
		ConverterRegistry.converters = converters;
	}
}