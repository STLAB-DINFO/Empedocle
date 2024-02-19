package it.unifi.ing.stlab.reflection.model.facts;

import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.List;
import java.util.Set;

public interface Fact {

	Type getType();
	void assignType(Type type);
	
	Set<? extends FactLink> listParents();
	
	Set<? extends FactLink> listChildren();
	Set<? extends FactLink> listActiveLinks();
	List<? extends FactLink> listChildrenOrdered();
	void refreshChildrenOrdered();
	
	FactContext getContext();
	void setContext(FactContext context);
	
	FactStatus getStatus();
	void setStatus(FactStatus status);
	
	boolean isEmpty();
	
	void assignDefaultValue(FactValue value);
	void accept(FactVisitor visitor);
	
	void delete();

}
