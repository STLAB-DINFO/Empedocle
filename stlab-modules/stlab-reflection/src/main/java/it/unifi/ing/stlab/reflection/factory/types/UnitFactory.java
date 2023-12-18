package it.unifi.ing.stlab.reflection.factory.types;

import it.unifi.ing.stlab.reflection.model.types.Unit;

import java.util.UUID;

public class UnitFactory {

	public static Unit createUnit() {
		return new Unit( UUID.randomUUID().toString() );
	}
}
