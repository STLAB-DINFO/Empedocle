package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactMergeAction 
	extends FakeCompactAction 
	implements MergeAction<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> {

	private final MergeActionImpl<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> delegate;

	
	public FakeCompactMergeAction() {
		delegate = new MergeActionImpl<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime>();
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
	public FakeCompactEntity getSource1() {
		return delegate.getSource1();
	}
	public void setSource1(FakeCompactEntity source1) {
		delegate.setSource1(source1);
	}
	public void assignSource1(FakeCompactEntity tracedEntity) {
		delegate.assignSource1(tracedEntity);
	}

	
	// Source2 methods
	public FakeCompactEntity getSource2() {
		return delegate.getSource2();
	}
	public void setSource2(FakeCompactEntity source2) {
		delegate.setSource2(source2);
	}
	public void assignSource2(FakeCompactEntity tracedEntity) {
		delegate.assignSource2(tracedEntity);
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
