package it.unifi.ing.stlab.reflection.lite.converter;

import it.unifi.ing.stlab.reflection.model.facts.Fact;

public interface FactConverter {

	String serialize(Fact fact);
	Fact deserialize(String content);
	
}
