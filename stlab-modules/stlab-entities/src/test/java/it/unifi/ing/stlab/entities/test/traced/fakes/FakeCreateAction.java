package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;

public class FakeCreateAction 
	extends FakeAction 
	implements CreateAction<FakeTracedEntity,FakeAction, FakeActor, FakeTime> {

	private CreateActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime> delegate;

	public FakeCreateAction() {
		delegate = new CreateActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime>();
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
