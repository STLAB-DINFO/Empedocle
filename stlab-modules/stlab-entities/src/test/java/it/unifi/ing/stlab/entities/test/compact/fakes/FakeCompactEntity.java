package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.compact.CompactEntityImpl;
import it.unifi.ing.stlab.entities.model.compact.CompactEntity;

import java.util.List;
import java.util.Set;

public class FakeCompactEntity implements CompactEntity<FakeCompactEntity,FakeCompactLink, FakeCompactAction>{

	private CompactEntityImpl<FakeCompactEntity,FakeCompactLink, FakeCompactAction> delegate;

	private String payload;
	
	public FakeCompactEntity() {
		 delegate = new CompactEntityImpl<FakeCompactEntity,FakeCompactLink, FakeCompactAction>();
		 delegate.setDelegator( this );
	}
	

	public void init() {
		delegate.init();
	}
	public void delete() {
		delegate.delete();
	}



	// Before
	public Set<FakeCompactEntity> listBefore() {
		return delegate.listBefore();
	}
	public Set<FakeCompactEntity> getBefore() {
		return delegate.getBefore();
	}
	public void setBefore(Set<FakeCompactEntity> before) {
		delegate.setBefore(before);
	}



	// After
	public Set<FakeCompactEntity> listAfter() {
		return delegate.listAfter();
	}
	public Set<FakeCompactEntity> getAfter() {
		return delegate.getAfter();
	}
	public void setAfter(Set<FakeCompactEntity> after) {
		delegate.setAfter(after);
	}

	// Origin
	public FakeCompactAction getOrigin() {
		return delegate.getOrigin();
	}
	public void setOrigin(FakeCompactAction origin) {
		delegate.setOrigin(origin);
	}

	// Destination
	public FakeCompactAction getDestination() {
		return delegate.getDestination();
	}
	public void setDestination(FakeCompactAction destination) {
		delegate.setDestination(destination);
	}



	// Ancestors
	public Set<FakeCompactEntity> listAncestors() {
		return delegate.listAncestors();
	}
	public Set<FakeCompactEntity> getAncestors() {
		return delegate.getAncestors();
	}
	public void setAncestors(Set<FakeCompactEntity> ancestors) {
		delegate.setAncestors(ancestors);
	}



	// Descendents
	public Set<FakeCompactEntity> listDescendents() {
		return delegate.listDescendents();
	}
	public Set<FakeCompactEntity> getDescendents() {
		return delegate.getDescendents();
	}
	public void setDescendents(Set<FakeCompactEntity> descendents) {
		delegate.setDescendents(descendents);
	}



	// Parents
	public Set<FakeCompactLink> listParents() {
		return delegate.listParents();
	}
	public Set<FakeCompactLink> getParents() {
		return delegate.getParents();
	}
	public void setParents(Set<FakeCompactLink> parents) {
		delegate.setParents(parents);
	}



	// Children
	public Set<FakeCompactLink> listChildren() {
		return delegate.listChildren();
	}
	public List<FakeCompactLink> listChildrenOrdered() {
		return delegate.listChildrenOrdered();
	}
	@Override
	public void refreshChildrenOrdered() {
		delegate.refreshChildrenOrdered();
	}
	public Set<FakeCompactLink> getChildren() {
		return delegate.getChildren();
	}
	public void setChildren(Set<FakeCompactLink> children) {
		delegate.setChildren(children);
	}

	
	
	public boolean isActive() {
		return delegate.isActive();
	}
	
	
	public Set<FakeCompactLink> listActiveLinks() {
		return delegate.listActiveLinks();
	}


	
	//
	// Undelegated methods
	//
	
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	
	@Override
	public boolean isEmpty() {
		return payload == null || "".equals( payload.trim() );
	}


	@Override
	public boolean sameAs(FakeCompactEntity entity) {
		if ( payload == null || entity.getPayload() == null ) 
			return false;
		
		return payload.equals( entity.getPayload() );
	}


	@Override
	public FakeCompactEntity copy() {
		FakeCompactEntity result = new FakeCompactEntity();
		result.setPayload( payload );
		return result;
	}

}
