package it.unifi.ing.stlab.entities.implementation.traced.actions;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;

public class MergeActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 
	
	extends ActionImpl<T,A,U,H>
	implements MergeAction<T,A,U,H>, Delegated<A> {

	private T source1;
	private T source2;
	private T target;
	
	@Override
	public void delete() {
		assignSource1( null );
		assignSource2( null );
		assignTarget( null );
		GarbageCollector.getInstance().garbage( getDelegator() );
	}
	
	@Override
	public T getSource1() {
		return source1;
	}

	public void setSource1(T source1) {
		this.source1 = source1;
	}

	@Override
	public void assignSource1(T newSource1 ) {
		if ( isCurrentSource1( newSource1 )) return;
		if ( !isValidSource1( newSource1 )) throw new IllegalArgumentException();
		if ( source1 != null && newSource1 != null ) throw new IllegalStateException();
		
		clearBefore();
		clearAfter();
		clearDestination1();
		
		source1 = newSource1;
		
		updateDestination1();
		updateBefore();
		updateAfter();
	}

	private boolean isCurrentSource1( T newSource ) {
		return source1 != null && source1.equals( newSource );
	}
	
	private boolean isValidSource1( T newSource1 ) {
		if ( newSource1 == null ) 
			return true;
		
		A destination = newSource1.getDestination();
		if ( destination != null && !destination.equals( getDelegator())) return false;

		if ( source2 != null && source2.equals( newSource1 )) return false;
        return target == null || !target.equals(newSource1);
    }
	
	private void clearDestination1() {
		if ( source1 != null ) {
			setDestination( source1, null );
		}
	}
	
	private void updateDestination1() {
		if ( source1 != null ) {
			setDestination( source1, getDelegator() );
		}
	}
	
	@Override
	public T getSource2() {
		return source2;
	}

	public void setSource2(T source2) {
		this.source2 = source2;
	}

	@Override
	public void assignSource2(T newSource2 ) {
		if ( isCurrentSource2( newSource2 )) return;
		if ( !isValidSource2( newSource2 )) throw new IllegalArgumentException();
		if ( source2 != null && newSource2 != null ) throw new IllegalStateException();
		
		clearBefore();
		clearAfter();
		clearDestination2();
		
		source2 = newSource2;
		
		updateDestination2();
		updateBefore();
		updateAfter();
	}

	private boolean isCurrentSource2( T newSource2 ) {
		return source2 != null && source2.equals( newSource2 );
	}
	
	private boolean isValidSource2( T newSource2 ) {
		if ( newSource2 == null ) 
			return true;
		
		A destination = newSource2.getDestination();
		if ( destination != null && !destination.equals( getDelegator())) return false;

		if ( source1 != null && source1.equals( newSource2 )) return false;
        return target == null || !target.equals(newSource2);
    }
	
	private void clearDestination2() {
		if ( source2 != null ) {
			setDestination( source2, null );
		}
	}
	
	private void updateDestination2() {
		if ( source2 != null ) {
			setDestination( source2, getDelegator() );
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

		if ( source1 != null && source1.equals( newTarget )) return false;
        return source2 == null || !source2.equals(newTarget);
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
		if ( target == null ) return;
		
		if ( source1 != null ) {
			for ( T after : target.listAfter() ) {
				getBefore( after ).removeAll( source1.listBefore() );
				getBefore( after ).add( after );
			}
		}

		if ( source2 != null ) {
			for ( T after : target.listAfter() ) {
				getBefore( after ).removeAll( source2.listBefore() );
				getBefore( after ).add( after );
			}
		}
	}
	
	private void clearAfter() {
		if ( target == null ) return;
		
		if ( source1 != null ) {
			for ( T before : source1.listBefore() ) {
				getAfter( before ).removeAll( target.listAfter() );
				getAfter( before ).add( before );
			}
		}

		if ( source2 != null ) {
			for ( T before : source2.listBefore() ) {
				getAfter( before ).removeAll( target.listAfter() );
				getAfter( before ).add( before );
			}
		}
	}
	
	private void updateBefore() {
		if ( source1 == null || source2 == null || target == null ) return;
		
		for ( T after : target.listAfter() ) {
			getBefore( after ).addAll( source1.listBefore() );
			getBefore( after ).addAll( source2.listBefore() );
		}
	}
	
	private void updateAfter() {
		if ( source1 == null || source2 == null || target == null ) return;
		
		for ( T before : source1.listBefore() ) {
			getAfter( before ).addAll( target.listAfter() );
		}
		
		for ( T before : source2.listBefore() ) {
			getAfter( before ).addAll( target.listAfter() );
		}
	}

}
