package it.unifi.ing.stlab.entities.implementation.traced.actions;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;


public class CreateActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 
	
	extends ActionImpl<T,A,U,H>
	implements CreateAction<T,A,U,H>, Delegated<A> {

	private T target;
	
	@Override
	public void delete() {
		assignTarget( null );
		GarbageCollector.getInstance().garbage( getDelegator() );
	}
	
	@Override
	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	@Override
	public void assignTarget(T newTarget ) {
		if ( isCurrentTarget( newTarget )) return;
		if ( !isValidTarget( newTarget )) throw new IllegalArgumentException();
		if ( target != null && newTarget != null ) throw new IllegalStateException();
		
		clearOrigin();
		target = newTarget;
		updateOrigin();
	}

	private boolean isCurrentTarget( T newTarget ) {
		return target != null && target.equals( newTarget );
	}
	
	private boolean isValidTarget( T newTarget ) {
		if ( newTarget == null ) 
			return true;

		A origin = newTarget.getOrigin();
		if ( origin != null && !origin.equals( getDelegator() )) 
			return false;

		return true;
	}
	
	private void clearOrigin() {
		if ( target != null ) {
			setOrigin( target, null );
		}
	}
	
	private void updateOrigin() {
		if ( target != null ) {
			setOrigin( target, getDelegator() );
		}
	}
	
	@Override
	public boolean isTerminal() {
		return false;
	}
}
