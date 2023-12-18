package it.unifi.ing.stlab.empedocle.factory.health.coding.atc;

import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCAnatomicalMainGroupCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCChemicalSubGroupCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCChemicalSubstanceCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCPharmacologicalSubGroupCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.atc.ATCTherapeuticMainGroupCode;

import java.util.UUID;


public class ATCCodeFactory {

	public static ATCAnatomicalMainGroupCode createAnatomicalMainGroupCode() {
		return new ATCAnatomicalMainGroupCode( UUID.randomUUID().toString() );
	}
	
	public static ATCTherapeuticMainGroupCode createTherapeuticMainGroupCode() {
		return new ATCTherapeuticMainGroupCode( UUID.randomUUID().toString() );
	}
	
	public static ATCPharmacologicalSubGroupCode createPharmacologicalSubGroupCode() {
		return new ATCPharmacologicalSubGroupCode( UUID.randomUUID().toString() );
	}
	
	public static ATCChemicalSubGroupCode createChemicalSubGroupCode() {
		return new ATCChemicalSubGroupCode( UUID.randomUUID().toString() );
	}
	
	public static ATCChemicalSubstanceCode createChemicalSubstanceCode() {
		return new ATCChemicalSubstanceCode( UUID.randomUUID().toString() );
	}

}
