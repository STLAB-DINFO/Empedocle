package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.Authorization;

import java.util.UUID;

public class AuthorizationFactory {

	public static Authorization createAuthorization() {
		return new Authorization( UUID.randomUUID().toString() );
	}
	
}
