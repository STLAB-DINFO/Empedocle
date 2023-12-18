package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.compact.UpdateLinkImpl;
import it.unifi.ing.stlab.entities.model.compact.UpdateLink;

public class FakeUpdateLink extends FakeCompactLink implements UpdateLink<FakeCompactEntity, FakeCompactLink>{

	private UpdateLinkImpl<FakeCompactEntity, FakeCompactLink> delegate;

	public FakeUpdateLink() {
		 delegate = new UpdateLinkImpl<FakeCompactEntity, FakeCompactLink>();
		 delegate.setDelegator( this );
	}
	
	
	public void delete() {
		delegate.delete();
	}

	// RefersTo methods
	public FakeCompactLink getRefersTo() {
		return delegate.getRefersTo();
	}
	public void setRefersTo(FakeCompactLink refersTo) {
		delegate.setRefersTo(refersTo);
	}
	public void assignRefersTo(FakeCompactLink refersTo) {
		delegate.assignRefersTo(refersTo);
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
