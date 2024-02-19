package it.unifi.ing.stlab.entities.test.composite.fakes;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;

public class FakeCompositeLink implements CompositeLink<FakeCompositeEntity,FakeCompositeLink> {

	private final CompositeLinkImpl<FakeCompositeEntity,FakeCompositeLink> delegate;
	
	public FakeCompositeLink() {
		delegate = new CompositeLinkImpl<FakeCompositeEntity,FakeCompositeLink>();
		delegate.setDelegator( this );
	}
	
	public void delete() {
		delegate.delete();
	}

	
	// Source methods
	public FakeCompositeEntity getSource() {
		return delegate.getSource();
	}
	public void setSource(FakeCompositeEntity source) {
		delegate.setSource(source);
	}
	public void assignSource(FakeCompositeEntity source) {
		delegate.assignSource(source);
	}

	// Target methods
	public FakeCompositeEntity getTarget() {
		return delegate.getTarget();
	}
	public void setTarget(FakeCompositeEntity target) {
		delegate.setTarget(target);
	}
	public void assignTarget(FakeCompositeEntity target) {
		delegate.assignTarget(target);
	}

	// Priority methods
	public Long getPriority() {
		return delegate.getPriority();
	}
	public void setPriority(Long priority) {
		delegate.setPriority(priority);
	}
	
}
