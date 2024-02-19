package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Local;

@Local
public interface FolderNumberGenerator {
	
	void generateFolderNumber(Fact f);

}
