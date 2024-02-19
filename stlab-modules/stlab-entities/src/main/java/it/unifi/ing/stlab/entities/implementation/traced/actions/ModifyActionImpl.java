package it.unifi.ing.stlab.entities.implementation.traced.actions;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;

public class ModifyActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 
	
	extends ActionImpl<T,A,U,H>
	implements ModifyAction<T,A,U,H>, Delegated<A> {

	private T source;
	private T target;

	@Override
	public void delete() {
		assignSource( null );
		assignTarget( null );
		GarbageCollector.getInstance().garbage( getDelegator() );
	}
	
	@Override
	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	@Override
	public void assignSource(T newSource ) {
		if ( isCurrentSource( newSource )) return;
		if ( !isValidSource( newSource )) throw new IllegalArgumentException();
		if ( source != null && newSource != null ) throw new IllegalStateException();
		
		clearBefore();
		clearAfter();
		clearDestination();
		
		source = newSource;
		
		updateDestination();
		updateBefore();
		updateAfter();
	}

	private boolean isCurrentSource( T newSource ) {
		return source != null && source.equals( newSource );
	}
	
	private boolean isValidSource( T newSource ) {
		if ( newSource == null ) 
			return true;
		
		A destination = newSource.getDestination();
		if ( destination != null && !destination.equals( getDelegator())) return false;

        return target == null || !target.equals(newSource);
    }
	
	private void clearDestination() {
		if ( source != null ) {
			setDestination( source, null );
		}
	}
	
	private void updateDestination() {
		if ( source != null ) {
			setDestination( source, getDelegator() );
		}
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
		
		clearBefore();
		clearAfter();
		clearOrigin();
		
		target = newTarget;
		
		updateOrigin();
		updateBefore();
		updateAfter();
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

        return source == null || !source.equals(newTarget);
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
	
	private void clearBefore() {
		if ( source == null || target == null ) return;
		
		for ( T after : target.listAfter() ) {
			getBefore( after ).removeAll( source.listBefore() );
			getBefore( after ).add( after );
		}
	}
	
	private void clearAfter() {
		if ( source == null || target == null ) return;
		
		for ( T before : source.listBefore() ) {
			getAfter( before ).removeAll( target.listAfter() );
			getAfter( before ).add( before );
		}
	}
	
	
	private void updateBefore() {
		if ( source == null || target == null ) return;
		
		for ( T after : target.listAfter() ) {
			getBefore( after ).addAll( source.listBefore() );
		}
	}
	private void updateAfter() {
		if ( source == null || target == null ) return;
		
		for ( T before : source.listBefore() ) {
			getAfter( before ).addAll( target.listAfter() );
		}
	}
}
