package it.unifi.ing.stlab.reflection.factory.types;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

import java.util.UUID;

public class PhenomenonFactory {

	public static Phenomenon createPhenomenon() {
		return new Phenomenon( UUID.randomUUID().toString() );
	}
}
