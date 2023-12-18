package it.unifi.ing.stlab.entities.utils;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class ClassHelper {

	public static boolean instanceOf( Object obj, Class<?> clazz ) {
		return clazz.isInstance( unproxy( obj ));
	}
	
	public static <T> T cast( Object obj, Class<T> clazz  ) {
		return clazz.cast( unproxy( obj ));
	}

	
	public static Object unproxy( Object proxied ) {
		if ( proxied == null ) return null;
		
	    if ( proxied instanceof HibernateProxy) {
	        Hibernate.initialize( proxied );
	       return ((HibernateProxy)proxied)
	        			.getHibernateLazyInitializer()
	        			.getImplementation();
	    } else {
	    	return proxied;
	    }
	}


}
