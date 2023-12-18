package it.unifi.ing.stlab.entities.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class CheckSumFactory {

	public static String createCheckSum( Object... objects ) {
		if ( objects == null || objects.length == 0 ) throw new IllegalArgumentException();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update( string( objects ).getBytes() );
			
			return new String( Base64.encodeBase64( md.digest() ));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException( "algorithm not found" );
		}
	}

	private static String string( Object[] objects ) {
		StringBuffer result = new StringBuffer();
		
		for ( int i = 0; i < objects.length; i++ ) {
			result.append( value( objects[ i ] ))
				  .append( ":" )
				  .append( type( objects[ i ]))
				  .append( ";" );
		}

		return result.toString();
	}
	
	private static String type( Object obj ) {
		if ( obj == null ) return Object.class.getCanonicalName();
		
		return obj.getClass().getCanonicalName();
	}
	
	private static String value( Object obj ) {
		if ( obj == null ) return "";
		
		return obj.toString().trim().toLowerCase();
	}
}
