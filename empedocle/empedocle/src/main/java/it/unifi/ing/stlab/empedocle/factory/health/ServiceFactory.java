package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.Service;

import java.util.UUID;

public class ServiceFactory {

	public static Service createService() {
		return new Service( UUID.randomUUID().toString() );
	}
}
