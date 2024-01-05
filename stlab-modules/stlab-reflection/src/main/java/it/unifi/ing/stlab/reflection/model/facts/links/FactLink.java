package it.unifi.ing.stlab.reflection.model.facts.links;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public interface FactLink {
	
	Fact getSource();
	Fact getTarget();
	
	TypeLink getType();
	void setType(TypeLink type);
	
	void setPriority(Long priority);
	Long getPriority();
	
	void delete();
	
}
