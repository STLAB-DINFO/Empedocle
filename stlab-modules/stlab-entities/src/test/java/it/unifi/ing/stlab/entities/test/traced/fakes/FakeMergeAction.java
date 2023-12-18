package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;

public class FakeMergeAction 
	extends FakeAction 
	implements MergeAction<FakeTracedEntity, FakeAction, FakeActor, FakeTime> {

	private MergeActionImpl<FakeTracedEntity, FakeAction, FakeActor, FakeTime> delegate;

	
	public FakeMergeAction() {
		delegate = new MergeActionImpl<FakeTracedEntity, FakeAction, FakeActor, FakeTime>();
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
	


	// Source1 methods
	public FakeTracedEntity getSource1() {
		return delegate.getSource1();
	}
	public void setSource1(FakeTracedEntity source1) {
		delegate.setSource1(source1);
	}
	public void assignSource1(FakeTracedEntity tracedEntity) {
		delegate.assignSource1(tracedEntity);
	}

	
	// Source2 methods
	public FakeTracedEntity getSource2() {
		return delegate.getSource2();
	}
	public void setSource2(FakeTracedEntity source2) {
		delegate.setSource2(source2);
	}
	public void assignSource2(FakeTracedEntity tracedEntity) {
		delegate.assignSource2(tracedEntity);
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
