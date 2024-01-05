package it.unifi.ing.stlab.entities.test.composite.fakes;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;

import java.util.List;
import java.util.Set;

public class FakeCompositeEntity implements CompositeEntity<FakeCompositeEntity,FakeCompositeLink> {

	private final CompositeEntityImpl<FakeCompositeEntity,FakeCompositeLink> delegate;

	public FakeCompositeEntity() {
		delegate = new CompositeEntityImpl<FakeCompositeEntity,FakeCompositeLink>();
		delegate.setDelegator( this );
	}
	
	
	
	public void init() {
		delegate.init();
	}
	public void delete() {
		delegate.delete();
	}

	// Ancestors methods
	public Set<FakeCompositeEntity> listAncestors() {
		return delegate.listAncestors();
	}
	public Set<FakeCompositeEntity> getAncestors() {
		return delegate.getAncestors();
	}
	public void setAncestors(Set<FakeCompositeEntity> ancestors) {
		delegate.setAncestors(ancestors);
	}


	// Descendents methods
	public Set<FakeCompositeEntity> listDescendents() {
		return delegate.listDescendents();
	}
	public Set<FakeCompositeEntity> getDescendents() {
		return delegate.getDescendents();
	}
	public void setDescendents(Set<FakeCompositeEntity> descendents) {
		delegate.setDescendents(descendents);
	}


	// Parents methods
	public Set<FakeCompositeLink> listParents() {
		return delegate.listParents();
	}
	public Set<FakeCompositeLink> getParents() {
		return delegate.getParents();
	}
	public void setParents(Set<FakeCompositeLink> parents) {
		delegate.setParents(parents);
	}


	// Children methods
	public Set<FakeCompositeLink> listChildren() {
		return delegate.listChildren();
	}
	public List<FakeCompositeLink> listChildrenOrdered() {
		return delegate.listChildrenOrdered();
	}
	@Override
	public void refreshChildrenOrdered() {
		delegate.refreshChildrenOrdered();
	}
	public Set<FakeCompositeLink> getChildren() {
		return delegate.getChildren();
	}
	public void setChildren(Set<FakeCompositeLink> children) {
		delegate.setChildren(children);
	}
}
