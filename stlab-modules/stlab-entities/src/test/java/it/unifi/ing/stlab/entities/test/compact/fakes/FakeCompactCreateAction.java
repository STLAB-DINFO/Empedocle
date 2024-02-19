package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactCreateAction 
	extends FakeCompactAction 
	implements CreateAction<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> {

	private final CreateActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> delegate;

	public FakeCompactCreateAction() {
		delegate = new CreateActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime>();
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
	public FakeCompactEntity getTarget() {
		return delegate.getTarget();
	}
	public void setTarget(FakeCompactEntity target) {
		delegate.setTarget(target);
	}
	public void assignTarget(FakeCompactEntity tracedEntity) {
		delegate.assignTarget(tracedEntity);
	}


	public boolean isTerminal() {
		return delegate.isTerminal();
	}

	
}
