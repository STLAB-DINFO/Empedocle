package it.unifi.ing.stlab.users.factory;

import it.unifi.ing.stlab.users.model.Qualification;

import java.util.UUID;

public class QualificationFactory {

	public static Qualification createQualification() {
		return new Qualification( UUID.randomUUID().toString() );
	}
}
