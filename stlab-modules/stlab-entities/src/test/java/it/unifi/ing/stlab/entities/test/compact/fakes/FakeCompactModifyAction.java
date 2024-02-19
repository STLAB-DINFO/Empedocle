package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactModifyAction 
	extends FakeCompactAction 
	implements ModifyAction<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> {

	private final ModifyActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> delegate;

	
	public FakeCompactModifyAction() {
		 delegate = new ModifyActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime>();
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
	public FakeCompactEntity getSource() {
		return delegate.getSource();
	}
	public void setSource(FakeCompactEntity source) {
		delegate.setSource(source);
	}
	public void assignSource(FakeCompactEntity tracedEntity) {
		delegate.assignSource(tracedEntity);
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
