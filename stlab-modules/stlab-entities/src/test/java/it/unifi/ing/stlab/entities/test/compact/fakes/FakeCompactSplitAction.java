package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactSplitAction 
	extends FakeCompactAction 
	implements SplitAction<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> {

	private SplitActionImpl<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> delegate;

	public FakeCompactSplitAction() {
		 delegate = new SplitActionImpl<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime>();
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

	
	// Target1 methods
	public FakeCompactEntity getTarget1() {
		return delegate.getTarget1();
	}
	public void setTarget1(FakeCompactEntity target1) {
		delegate.setTarget1(target1);
	}
	public void assignTarget1(FakeCompactEntity tracedEntity) {
		delegate.assignTarget1(tracedEntity);
	}

	
	// Target2 methods
	public FakeCompactEntity getTarget2() {
		return delegate.getTarget2();
	}
	public void setTarget2(FakeCompactEntity target2) {
		delegate.setTarget2(target2);
	}
	public void assignTarget2(FakeCompactEntity tracedEntity) {
		delegate.assignTarget2(tracedEntity);
	}

	
	public boolean isTerminal() {
		return delegate.isTerminal();
	}
}
