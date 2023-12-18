package it.unifi.ing.stlab.users.factory;

import it.unifi.ing.stlab.users.model.Role;

import java.util.UUID;

public class RoleFactory {

	
	public static Role createRole() {
		return new Role( UUID.randomUUID().toString() );
	}
}
