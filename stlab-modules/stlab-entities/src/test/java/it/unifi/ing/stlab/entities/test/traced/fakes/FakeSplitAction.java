package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;

public class FakeSplitAction 
	extends FakeAction 
	implements SplitAction<FakeTracedEntity, FakeAction, FakeActor, FakeTime> {

	private SplitActionImpl<FakeTracedEntity, FakeAction, FakeActor, FakeTime> delegate;

	public FakeSplitAction() {
		 delegate = new SplitActionImpl<FakeTracedEntity, FakeAction, FakeActor, FakeTime>();
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

	
	// Target1 methods
	public FakeTracedEntity getTarget1() {
		return delegate.getTarget1();
	}
	public void setTarget1(FakeTracedEntity target1) {
		delegate.setTarget1(target1);
	}
	public void assignTarget1(FakeTracedEntity tracedEntity) {
		delegate.assignTarget1(tracedEntity);
	}

	
	// Target2 methods
	public FakeTracedEntity getTarget2() {
		return delegate.getTarget2();
	}
	public void setTarget2(FakeTracedEntity target2) {
		delegate.setTarget2(target2);
	}
	public void assignTarget2(FakeTracedEntity tracedEntity) {
		delegate.assignTarget2(tracedEntity);
	}

	
	public boolean isTerminal() {
		return delegate.isTerminal();
	}
}
