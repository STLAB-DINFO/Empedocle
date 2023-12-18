package it.unifi.ing.stlab.users.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


public class PasswordHash {

	public String createPasswordKey( String password ) {
		if ( password == null ) throw new IllegalArgumentException( "password cannot be null" );
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update( password.getBytes() );
			
			return new String( Base64.encodeBase64( md.digest() ));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException( "algorithm not found" );
		}
	}
}
