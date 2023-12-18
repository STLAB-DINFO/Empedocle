package it.unifi.ing.stlab.reflection.impl.factory;

import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QualitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QuantitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TemporalFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;

import java.lang.reflect.Constructor;
import java.util.UUID;

public class FactFactory {

	public static TextualFactImpl createTextual() {
		return createInstance( TextualFactImpl.class );
	}

	public static QuantitativeFactImpl createQuantitative() {
		return createInstance( QuantitativeFactImpl.class );
	}

	public static QualitativeFactImpl createQualitative() {
		return createInstance( QualitativeFactImpl.class );
	}
	
	public static TemporalFactImpl createTemporal() {
		return createInstance( TemporalFactImpl.class );
	}

	public static CompositeFactImpl createComposite() {
		return createInstance( CompositeFactImpl.class );
	}
	
	
	private static <T extends FactImpl> T createInstance( Class<T> type ) { 
		try {
			Constructor<T> constructor = type.getConstructor( String.class );
			T result = constructor.newInstance( UUID.randomUUID().toString() );
			result.init();
			return result;
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

}
