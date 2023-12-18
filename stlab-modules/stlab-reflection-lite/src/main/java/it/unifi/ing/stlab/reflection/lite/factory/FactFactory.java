package it.unifi.ing.stlab.reflection.lite.factory;

import it.unifi.ing.stlab.reflection.lite.model.facts.CompositeFactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.FactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.QualitativeFactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.QuantitativeFactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.TemporalFactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.TextualFactLite;

import java.lang.reflect.Constructor;

public class FactFactory {

	public static TextualFactLite createTextual() {
		return createInstance( TextualFactLite.class );
	}

	public static QuantitativeFactLite createQuantitative() {
		return createInstance( QuantitativeFactLite.class );
	}

	public static QualitativeFactLite createQualitative() {
		return createInstance( QualitativeFactLite.class );
	}
	
	public static TemporalFactLite createTemporal() {
		return createInstance( TemporalFactLite.class );
	}

	public static CompositeFactLite createComposite() {
		return createInstance( CompositeFactLite.class );
	}
	
	
	private static <T extends FactLite> T createInstance( Class<T> type ) { 
		try {
			Constructor<T> constructor = type.getConstructor();
			T result = constructor.newInstance();
			result.init();
			return result;
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

}
