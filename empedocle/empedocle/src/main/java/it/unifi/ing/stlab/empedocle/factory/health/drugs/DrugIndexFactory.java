package it.unifi.ing.stlab.empedocle.factory.health.drugs;

import java.util.UUID;

import it.unifi.ing.stlab.empedocle.model.health.drugs.DrugIndex;

public class DrugIndexFactory {

	public static DrugIndex createDrugIndex() {
		return new DrugIndex( UUID.randomUUID().toString() );
	}
}
