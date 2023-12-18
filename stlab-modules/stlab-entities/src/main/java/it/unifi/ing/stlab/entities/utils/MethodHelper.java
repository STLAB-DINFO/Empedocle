package it.unifi.ing.stlab.entities.utils;

import java.lang.reflect.Method;

public class MethodHelper {

	public static Method getMethod( Class<?> c, String name ) {
		if ( c == null ) {
			return null;
		}
		try {
			for ( Method method : c.getDeclaredMethods() ) {
				if ( method.getName().equals( name ) ) {
					method.setAccessible( true );
					return method;
				}
			}

			return getMethod( c.getSuperclass(), name );
		} catch (SecurityException e) {
			throw new RuntimeException( e );
		}
	}
	
}
