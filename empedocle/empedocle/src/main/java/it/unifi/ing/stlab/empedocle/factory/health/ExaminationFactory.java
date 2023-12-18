package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.Examination;

import java.util.UUID;

public class ExaminationFactory {

	public static Examination createExamination() {
		return new Examination( UUID.randomUUID().toString() );
	}
}
