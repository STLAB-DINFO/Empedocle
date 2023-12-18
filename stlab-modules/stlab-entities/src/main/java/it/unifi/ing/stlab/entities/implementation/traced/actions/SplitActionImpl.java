package it.unifi.ing.stlab.entities.implementation.traced.actions;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;

public class SplitActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 
	
	extends ActionImpl<T,A,U,H>
	implements SplitAction<T,A,U,H>, Delegated<A> {

	private T source;
	private T target1;
	private T target2;
	
	@Override
	public void delete() {
		assignSource( null );
		assignTarget1( null );
		assignTarget2( null );
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

		if ( target1 != null && target1.equals( newSource )) return false;
		if ( target2 != null && target2.equals( newSource )) return false;
		
		return true;
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
	public T getTarget1() {
		return target1;
	}

	public void setTarget1(T target1) {
		this.target1 = target1;
	}

	@Override
	public void assignTarget1(T newTarget1 ) {
		if ( isCurrentTarget1( newTarget1 )) return;
		if ( !isValidTarget1( newTarget1 )) throw new IllegalArgumentException();
		if ( target1 != null && newTarget1 != null ) throw new IllegalStateException();
		
		clearBefore();
		clearAfter();
		clearOrigin1()
		;
		target1 = newTarget1;
		
		updateOrigin1();
		updateBefore();
		updateAfter();
	}

	private boolean isCurrentTarget1( T newTarget1 ) {
		return target1 != null && target1.equals( newTarget1 );
	}
	
	private boolean isValidTarget1( T newTarget1 ) {
		if ( newTarget1 == null ) 
			return true;

		A origin = newTarget1.getOrigin();
		if ( origin != null && !origin.equals( getDelegator() )) 
			return false;

		if ( source != null && source.equals( newTarget1 )) return false;
		if ( target2 != null && target2.equals( newTarget1 )) return false;
		
		return true;
	}
	
	private void clearOrigin1() {
		if ( target1 != null ) {
			setOrigin( target1, null );
		}
	}
	
	private void updateOrigin1() {
		if ( target1 != null ) {
			setOrigin( target1, getDelegator() );
		}
	}

	@Override
	public T getTarget2() {
		return target2;
	}

	public void setTarget2(T target2) {
		this.target2 = target2;
	}

	@Override
	public void assignTarget2(T newTarget2 ) {
		if ( isCurrentTarget2( newTarget2 )) return;
		if ( !isValidTarget2( newTarget2 )) throw new IllegalArgumentException();
		if ( target2 != null && newTarget2 != null ) throw new IllegalStateException();
		
		clearBefore();
		clearAfter();
		clearOrigin2()
		;
		target2 = newTarget2;
		
		updateOrigin2();
		updateBefore();
		updateAfter();
	}

	private boolean isCurrentTarget2( T newTarget2 ) {
		return target2 != null && target2.equals( newTarget2 );
	}
	
	private boolean isValidTarget2( T newTarget2 ) {
		if ( newTarget2 == null ) 
			return true;

		A origin = newTarget2.getOrigin();
		if ( origin != null && !origin.equals( getDelegator() )) 
			return false;

		if ( source != null && source.equals( newTarget2 )) return false;
		if ( target1 != null && target1.equals( newTarget2 )) return false;
		
		return true;
	}
	
	private void clearOrigin2() {
		if ( target2 != null ) {
			setOrigin( target2, null );
		}
	}
	
	private void updateOrigin2() {
		if ( target2 != null ) {
			setOrigin( target2, getDelegator() );
		}
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
	
	private void clearBefore() {
		if ( source == null ) return;
	
		if ( target1 != null ) {
			for ( T after : target1.listAfter() ) {
				getBefore( after ).removeAll( source.listBefore() );
				getBefore( after ).add( after );
			}
		}

		if ( target2 != null )  {
			for ( T after : target2.listAfter() ) {
				getBefore( after ).removeAll( source.listBefore() );
				getBefore( after ).add( after );
			}
		}
	}

	
	private void clearAfter() {
		if ( source == null ) return;

		if ( target1 != null ) {
			for ( T before : source.listBefore() ) {
				getAfter( before ).removeAll( target1.listAfter() );
				getAfter( before ).add( before );
			}
		}

		if ( target2 != null ) {
			for ( T before : source.listBefore() ) {
				getAfter( before ).removeAll( target2.listAfter() );
				getAfter( before ).add( before );
			}
		}
	}
	
	private void updateBefore() {
		if ( source == null ) return;
		
		if ( target1 != null ) {
			for ( T after : target1.listAfter() ) {
				getBefore( after ).addAll( source.listBefore() );
			}
		}

		if ( target2 != null ) {
			for ( T after : target2.listAfter() ) {
				getBefore( after ).addAll( source.listBefore() );
			}
		}
	}
	
	private void updateAfter() {
		if ( source == null ) return;

		if ( target1 != null ) {
			for ( T before : source.listBefore() ) {
				getAfter( before ).addAll( target1.listAfter() );
			}
		}

		if ( target2 != null ) {
			for ( T before : source.listBefore() ) {
				getAfter( before ).addAll( target2.listAfter() );
			}
		}
	}

}
