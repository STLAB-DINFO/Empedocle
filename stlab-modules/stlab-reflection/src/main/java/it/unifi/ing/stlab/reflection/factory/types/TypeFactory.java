package it.unifi.ing.stlab.reflection.factory.types;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.lang.reflect.Constructor;
import java.util.UUID;

public class TypeFactory {

	public static TextualType createTextualType() {
		return createInstance( TextualType.class );
	}

	public static QuantitativeType createQuantitativeType() {
		return createInstance( QuantitativeType.class );
	}

	public static EnumeratedType createEnumeratedType() {
		return createInstance( EnumeratedType.class );
	}

	public static QueriedType createQueriedType() {
		return createInstance( QueriedType.class );
	}
	
	public static TemporalType createTemporalType() {
		return createInstance( TemporalType.class );
	}

	public static CompositeType createCompositeType() {
		return createInstance( CompositeType.class );
	}
	
	private static <T extends Type> T createInstance( Class<T> type ) { 
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
