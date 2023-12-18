package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.Fact;

public interface FieldRetriever {

	public String retrieve(Fact fact, String path);
	
}
