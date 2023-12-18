package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactDeleteAction 
	extends FakeCompactAction 
	implements DeleteAction<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> {

	private DeleteActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> delegate;

	public FakeCompactDeleteAction() {
		 delegate = new DeleteActionImpl<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime>();
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

	
	public boolean isTerminal() {
		return delegate.isTerminal();
	}
}
