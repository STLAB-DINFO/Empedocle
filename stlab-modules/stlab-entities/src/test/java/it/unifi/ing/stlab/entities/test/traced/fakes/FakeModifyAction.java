package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;

public class FakeModifyAction 
	extends FakeAction 
	implements ModifyAction<FakeTracedEntity,FakeAction, FakeActor, FakeTime> {

	private final ModifyActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime> delegate;

	
	public FakeModifyAction() {
		 delegate = new ModifyActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime>();
		 delegate.setDelegator( this );
	}
	
	
	
	public void delete() {
		delegate.delete();
	}

	


	// Author methods
	public FakeActor getAuthor() {
		return delegate.getAuthor();
	}
	public void setAuthor(FakeActor author) {
		delegate.setAuthor(author);
	}

	// Time methods
	public FakeTime getTime() {
		return delegate.getTime();
	}
	public void setTime(FakeTime time) {
		delegate.setTime(time);
	}
	

	// Source methods
	public FakeTracedEntity getSource() {
		return delegate.getSource();
	}
	public void setSource(FakeTracedEntity source) {
		delegate.setSource(source);
	}
	public void assignSource(FakeTracedEntity tracedEntity) {
		delegate.assignSource(tracedEntity);
	}

	
	// Target methods
	public FakeTracedEntity getTarget() {
		return delegate.getTarget();
	}
	public void setTarget(FakeTracedEntity target) {
		delegate.setTarget(target);
	}
	public void assignTarget(FakeTracedEntity tracedEntity) {
		delegate.assignTarget(tracedEntity);
	}


	public boolean isTerminal() {
		return delegate.isTerminal();
	}
	
}
