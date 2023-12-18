package it.unifi.ing.stlab.entities.implementation.compact;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;

public abstract class CompactLinkImpl 
	<T extends CompactEntity<T,L,?>, 
	L extends CompactLink<T,L>> 

	implements CompactLink<T,L>, Delegated<L> {

	private L delegator;
	private CompositeLinkImpl<T,L> delegate;

	public CompactLinkImpl() {
		delegate  = new CompositeLinkImpl<T,L>();
	}
	
	@Override
	public L getDelegator() {
		return delegator;
	}

	@Override
	public void setDelegator(L delegator) {
		this.delegator = delegator;
		delegate.setDelegator( delegator );
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
	public T getSource() {
		return delegate.getSource();
	}
	public void setSource(T source) {
		delegate.setSource(source);
	}
	public void assignSource(T newSource) {
		delegate.assignSource(newSource);
	}


	// Target methods
	public T getTarget() {
		return delegate.getTarget();
	}
	public void setTarget(T target) {
		delegate.setTarget(target);
	}
	public void assignTarget(T newTarget) {
		delegate.assignTarget(newTarget);
	}
	
}
