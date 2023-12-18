package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;

import java.util.UUID;

public class ExaminationTypeFactory {

	
	public static ExaminationType createExaminationType() {
		return new ExaminationType( UUID.randomUUID().toString() );
	}
}
