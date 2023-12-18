package it.unifi.ing.stlab.reflection.factory.types;

import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.UUID;

public class UnitUseFactory {

	public static UnitUse createUnitUse() {
		return new UnitUse( UUID.randomUUID().toString() );
	}
	
}
