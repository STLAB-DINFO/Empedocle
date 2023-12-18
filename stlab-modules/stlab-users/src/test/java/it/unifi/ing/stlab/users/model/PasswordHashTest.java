package it.unifi.ing.stlab.users.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PasswordHashTest {

	protected String password;
	protected PasswordHash passwordHash;
	
	@Before
	public void setUp() {
		password = "abcdefgh";
		passwordHash = new PasswordHash();
	}
	
	@Test
	public void testNotNull() {
		assertNotNull( passwordHash.createPasswordKey( password ));
		assertTrue( passwordHash.createPasswordKey( password ).length() > 0 );
	}
	
	public void testHash() {
		String hash1 = passwordHash.createPasswordKey( password );
		String hash2 = passwordHash.createPasswordKey( password );
		
		assertTrue( hash1.equals( hash2 ));
	}
}
