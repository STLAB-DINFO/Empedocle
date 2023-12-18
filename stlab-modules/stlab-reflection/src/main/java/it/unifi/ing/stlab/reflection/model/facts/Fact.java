package it.unifi.ing.stlab.reflection.model.facts;

import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.List;
import java.util.Set;

public interface Fact {

	public Type getType();
	public void assignType(Type type);
	
	public Set<? extends FactLink> listParents();
	
	public Set<? extends FactLink> listChildren();
	public Set<? extends FactLink> listActiveLinks();
	public List<? extends FactLink> listChildrenOrdered();
	public void refreshChildrenOrdered();
	
	public FactContext getContext();
	public void setContext(FactContext context);
	
	public FactStatus getStatus();
	public void setStatus(FactStatus status);
	
	public boolean isEmpty();
	
	public void assignDefaultValue(FactValue value);
	public void accept(FactVisitor visitor);
	
	public void delete();

}
