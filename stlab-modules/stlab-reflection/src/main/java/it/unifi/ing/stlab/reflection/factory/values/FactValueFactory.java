package it.unifi.ing.stlab.reflection.factory.values;

import it.unifi.ing.stlab.reflection.model.facts.values.QualitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.QuantitativeFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TemporalFactValue;
import it.unifi.ing.stlab.reflection.model.facts.values.TextualFactValue;

import java.util.UUID;

public class FactValueFactory {

	public static TextualFactValue createTextualValue() {
		return new TextualFactValue( UUID.randomUUID().toString() );
	}

	public static QuantitativeFactValue createQuantitativeValue() {
		return new QuantitativeFactValue( UUID.randomUUID().toString() );
	}

	public static QualitativeFactValue createQualitativeValue() {
		return new QualitativeFactValue( UUID.randomUUID().toString() );
	}
	
	public static TemporalFactValue createTemporalValue() {
		return new TemporalFactValue( UUID.randomUUID().toString() );
	}

}
