package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.Icd9cm;

import java.util.UUID;

public class Icd9cmFactory {

	public static Icd9cm createIcd9cm() {
		return new Icd9cm( UUID.randomUUID().toString() );
	}
	
}
