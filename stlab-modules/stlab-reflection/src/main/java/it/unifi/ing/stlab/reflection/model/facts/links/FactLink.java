package it.unifi.ing.stlab.reflection.model.facts.links;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public interface FactLink {
	
	public Fact getSource();
	public Fact getTarget();
	
	public TypeLink getType();
	public void setType(TypeLink type);
	
	public void setPriority(Long priority);
	public Long getPriority();
	
	public void delete();
	
}
