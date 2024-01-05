package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.compact.InsertLinkImpl;
import it.unifi.ing.stlab.entities.model.compact.InsertLink;

public class FakeInsertLink extends FakeCompactLink implements InsertLink<FakeCompactEntity,FakeCompactLink>  {

	private final InsertLinkImpl<FakeCompactEntity, FakeCompactLink> delegate;

	public FakeInsertLink() {
		delegate = new InsertLinkImpl<FakeCompactEntity, FakeCompactLink>();
		delegate.setDelegator( this );
	}
	
	
	public void delete() {
		delegate.delete();
	}

	
	// Priority methods
	public Long getPriority() {
		return delegate.getPriority();
	}
	public void setPriority(Long priority) {
		delegate.setPriority(priority);
	}

	
	// Source methods
	public FakeCompactEntity getSource() {
		return delegate.getSource();
	}
	public void setSource(FakeCompactEntity source) {
		delegate.setSource(source);
	}
	public void assignSource(FakeCompactEntity newSource) {
		delegate.assignSource(newSource);
	}

	
	// Target methods
	public FakeCompactEntity getTarget() {
		return delegate.getTarget();
	}
	public void setTarget(FakeCompactEntity target) {
		delegate.setTarget(target);
	}
	public void assignTarget(FakeCompactEntity newTarget) {
		delegate.assignTarget(newTarget);
	}
	
}
