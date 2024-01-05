package it.unifi.ing.stlab.empedocle.actions.staff;

import it.unifi.ing.stlab.users.model.PasswordHash;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PasswordGenerator {

	private static final String defaultPassword = "12345678";

	public String generateEncryptedDefaultPassword() {
		return generateEncryptedPassword( defaultPassword );
	}

	public String generateEncryptedPassword( String password ) {
		return new PasswordHash().createPasswordKey( password );
	}

	public String generateEncryptedRandomPassword() {
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes( array );
		String generatedString = new String( array, StandardCharsets.UTF_8);

		return generateEncryptedPassword( generatedString );
	}
}
