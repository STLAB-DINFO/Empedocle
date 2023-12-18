package it.unifi.ing.stlab.test;

import java.lang.reflect.Field;

public class FieldUtils {

	public static void assignField( Object obj, String fieldName, Object value ) throws RuntimeException {
		try {
			Field field = null;
			Class<?> clazz = obj.getClass();
			while(field == null) {
				field = getField( clazz, fieldName );
				clazz = clazz.getSuperclass();
			}
			
			field.setAccessible( true );
			field.set( obj, value );
		} catch (IllegalAccessException e) {
			throw new RuntimeException( e );
		}
	}
	
	private static Field getField(Class<?> clazz, String fieldName) {
		for(Field field : clazz.getDeclaredFields()) {
			if( field.getName().equals(fieldName) ) {
				return field;
			}
		}
		
		return null;
		
	}
	
}
