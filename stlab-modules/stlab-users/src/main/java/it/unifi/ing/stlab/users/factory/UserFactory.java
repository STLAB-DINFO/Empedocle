package it.unifi.ing.stlab.users.factory;

import it.unifi.ing.stlab.users.model.User;

import java.util.UUID;

public class UserFactory {

	public static User createUser() {
		return new User( UUID.randomUUID().toString() );
	}
	
}
