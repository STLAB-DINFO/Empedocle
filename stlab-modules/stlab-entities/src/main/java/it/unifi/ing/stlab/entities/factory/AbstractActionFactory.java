package it.unifi.ing.stlab.entities.factory;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;

public abstract class AbstractActionFactory 
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> {

	
	@SuppressWarnings("unchecked")
	public A createAction( U author, H time, T target ) {
		CreateAction<T,A,U,H> action = (CreateAction<T, A, U, H>) createAction();
	
		action.setAuthor( author );
		action.setTime( time );
		action.assignTarget( target );
		
		return (A)action;
	}
	protected abstract A createAction();
	
	
	@SuppressWarnings("unchecked")
	public A modifyAction( U author, H time, T source, T target ) {
		ModifyAction<T,A,U,H> action = (ModifyAction<T, A, U, H>) modifyAction();
	
		action.setAuthor( author );
		action.setTime( time );
		action.assignSource( source );
		action.assignTarget( target );
		
		return (A)action;
	}
	protected abstract A modifyAction();
	
	
	@SuppressWarnings("unchecked")
	public A mergeAction( U author, H time, T source1, T source2, T target ) {
		MergeAction<T,A,U,H> action = (MergeAction<T, A, U, H>) mergeAction();
	
		action.setAuthor( author );
		action.setTime( time );
		action.assignSource1( source1 );
		action.assignSource2( source2 );
		action.assignTarget( target );
		
		return (A)action;
	}
	protected abstract A mergeAction();

	
	@SuppressWarnings("unchecked")
	public A splitAction( U author, H time, T source, T target1, T target2 ) {
		SplitAction<T,A,U,H> action = (SplitAction<T, A, U, H>) splitAction();
	
		action.setAuthor( author );
		action.setTime( time );
		action.assignSource( source );
		action.assignTarget1( target1 );
		action.assignTarget2( target2 );
		
		return (A)action;
	}
	protected abstract A splitAction();
	
	@SuppressWarnings("unchecked")
	public A deleteAction( U author, H time, T source ) {
		DeleteAction<T,A,U,H> action = (DeleteAction<T, A, U, H>) deleteAction();
	
		action.setAuthor( author );
		action.setTime( time );
		action.assignSource( source );
		
		return (A)action;
	}
	protected abstract A deleteAction();
	
}
