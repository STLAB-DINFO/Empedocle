package it.unifi.ing.stlab.reflection.lite.converter;

import it.unifi.ing.stlab.reflection.model.facts.Fact;

public interface FactConverter {

	public String serialize( Fact fact );
	public Fact deserialize( String content );
	
}
