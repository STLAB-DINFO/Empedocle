package it.unifi.ing.stlab.patients.factory;

import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;

import java.util.UUID;

public class PatientFactory {

	public static Patient createPatient() {
		Patient result = new Patient( UUID.randomUUID().toString() );
		result.init();
		return result;
	}
	
	public static PatientIdentifier createPatientIdentifier() {
		PatientIdentifier result = new PatientIdentifier( UUID.randomUUID().toString() );
		return result;
	}
}
