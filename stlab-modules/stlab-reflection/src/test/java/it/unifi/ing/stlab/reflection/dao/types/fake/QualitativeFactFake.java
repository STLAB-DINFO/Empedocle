package it.unifi.ing.stlab.reflection.dao.types.fake;

import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.List;
import java.util.Set;

public class QualitativeFactFake implements QualitativeFact {

	private Phenomenon phenomenon;
	private Type type;
	
	
	public Phenomenon getPhenomenon() {
		return this.phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	
	public Type getType() {
		return this.type;
	}
	public void assignType(Type type) {
		this.type = type;
	}

	
	//
	// Unimplemented Methods
	//

	public boolean isEmpty() {
		return false;
	}
	public void accept(FactVisitor visitor) {
	}
	public void setContext(FactContext context) {
	}
	public Set<? extends FactLink> listParents() {
		return null;
	}
	public Set<? extends FactLink> listChildren() {
		return null;
	}
	public Set<? extends FactLink> listActiveLinks() {
		return null;
	}
	public List<? extends FactLink> listChildrenOrdered() {
		return null;
	}
	public Long getId() {
		return null;
	}
	public FactContext getContext() {
		return null;
	}
	public void delete() {
	}
	public void assignDefaultValue(FactValue value) {
	}
	public FactStatus getStatus() {
		return null;
	}
	public void setStatus(FactStatus status) {
	}
	public void refreshChildrenOrdered() {
	}
}
