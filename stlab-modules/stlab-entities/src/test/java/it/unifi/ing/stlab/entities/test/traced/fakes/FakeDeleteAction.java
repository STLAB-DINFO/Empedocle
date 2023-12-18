package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;

public class FakeDeleteAction 
	extends FakeAction 
	implements DeleteAction<FakeTracedEntity, FakeAction, FakeActor, FakeTime> {

	private DeleteActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime> delegate;

	public FakeDeleteAction() {
		 delegate = new DeleteActionImpl<FakeTracedEntity,FakeAction, FakeActor, FakeTime>();
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

	
	public boolean isTerminal() {
		return delegate.isTerminal();
	}
}
