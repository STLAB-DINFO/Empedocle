package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;

import java.util.Set;

public class FakeTracedEntity implements TracedEntity<FakeTracedEntity,FakeAction> {

	private TracedEntityImpl<FakeTracedEntity, FakeAction> delegate;
	private String payload;
	
	public FakeTracedEntity() {
		delegate = new TracedEntityImpl<FakeTracedEntity, FakeAction>();
		delegate.setDelegator( this );
	}
	
	
	public void init() {
		delegate.init();
	}

	public void delete() {
		delegate.delete();
	}

	// Payload
	public TracedEntityImpl<FakeTracedEntity, FakeAction> getDelegate() {
		return delegate;
	}
	public void setDelegate(TracedEntityImpl<FakeTracedEntity, FakeAction> delegate) {
		this.delegate = delegate;
	}
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	// Before methods
	public Set<FakeTracedEntity> listBefore() {
		return delegate.listBefore();
	}
	public Set<FakeTracedEntity> getBefore() {
		return delegate.getBefore();
	}
	public void setBefore(Set<FakeTracedEntity> before) {
		delegate.setBefore(before);
	}

	
	// After methods
	public Set<FakeTracedEntity> listAfter() {
		return delegate.listAfter();
	}
	public Set<FakeTracedEntity> getAfter() {
		return delegate.getAfter();
	}
	public void setAfter(Set<FakeTracedEntity> after) {
		delegate.setAfter(after);
	}

	
	// Origin methods
	public FakeAction getOrigin() {
		return delegate.getOrigin();
	}

	public void setOrigin(FakeAction origin) {
		delegate.setOrigin(origin);
	}

	
	// Destination methods
	public FakeAction getDestination() {
		return delegate.getDestination();
	}

	public void setDestination(FakeAction destination) {
		delegate.setDestination(destination);
	}
	
	
	public boolean isActive() {
		return delegate.isActive();
	}

	@Override
	public boolean isEmpty() {
		if ( payload == null || "".equals( payload.trim() )) return true;
		
		return false;
	}

	@Override
	public boolean sameAs(FakeTracedEntity entity) {
		if ( isEmpty() && entity.isEmpty() || payload.equals(entity.getPayload())) return true;
		
		return false;
	}

	@Override
	public FakeTracedEntity copy() {
		FakeTracedEntity result = new FakeTracedEntity();
		result.setPayload( payload );
		return result;
	}
	
}
