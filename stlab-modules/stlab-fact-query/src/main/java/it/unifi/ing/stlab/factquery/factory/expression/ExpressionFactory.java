package it.unifi.ing.stlab.factquery.factory.expression;

import it.unifi.ing.stlab.factquery.model.expression.AndExpression;
import it.unifi.ing.stlab.factquery.model.expression.AtomicExpression;
import it.unifi.ing.stlab.factquery.model.expression.Expression;
import it.unifi.ing.stlab.factquery.model.expression.OrExpression;

import java.lang.reflect.Constructor;
import java.util.UUID;

public class ExpressionFactory {

	public static AtomicExpression createAtomicExpression() {
		return createInstance( AtomicExpression.class );
		
	}
	
	public static AndExpression createAndExpression() {
		return createInstance( AndExpression.class );
		
	}
	
	public static OrExpression createOrExpression() {
		return createInstance( OrExpression.class );
		
	}
	
	private static <T extends Expression> T createInstance( Class<T> type ) { 
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
