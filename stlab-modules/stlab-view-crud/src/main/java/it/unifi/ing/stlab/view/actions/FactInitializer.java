package it.unifi.ing.stlab.view.actions;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.Type;

public interface FactInitializer {

	public abstract Fact init(Type type);
	
}
