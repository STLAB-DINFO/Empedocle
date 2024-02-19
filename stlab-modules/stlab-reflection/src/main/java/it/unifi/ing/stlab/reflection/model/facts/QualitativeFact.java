package it.unifi.ing.stlab.reflection.model.facts;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;

public interface QualitativeFact extends Fact {

	Phenomenon getPhenomenon();
	void setPhenomenon(Phenomenon phenomenon);
	
}
