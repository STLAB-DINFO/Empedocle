package it.unifi.ing.stlab.entities.utils;

import java.lang.reflect.Field;

public class FieldHelper {

	public static void write( Object obj, String fieldName,  Object value ) throws RuntimeException {
		try {
			getField( obj.getClass(), fieldName).set( obj, value );
		} catch (IllegalAccessException e) {
			throw new RuntimeException( e );
		}
	}	

	public static Object read( Object obj, String fieldName ) {
		try {
			return getField( obj.getClass(), fieldName ).get( obj );
		} catch (IllegalArgumentException e) {
			throw new RuntimeException( e );
		} catch (IllegalAccessException e) {
			throw new RuntimeException( e );
		}
	}	
	
	public static Field getField( Class<?> c, String f ) {
		if ( c == null ) {
			return null;
		}
		try {
			Field result = c.getDeclaredField( f );
			result.setAccessible( true );
			return result;
		} catch (SecurityException e) {
			throw new RuntimeException( e );
		} catch (NoSuchFieldException e) {
			return getField( c.getSuperclass(), f );
		}
	}
}
