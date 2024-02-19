package it.unifi.ing.stlab.entities.implementation.traced.actions;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;

public class DeleteActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 
	
	extends ActionImpl<T,A,U,H>
	implements DeleteAction<T,A,U,H>, Delegated<A> {

	private T source;
	
	@Override
	public void delete() {
		assignSource( null );
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
		
		clearDestination();
		source = newSource;
		updateDestination();
	}

	private boolean isCurrentSource( T newSource ) {
		return source != null && source.equals( newSource );
	}
	
	private boolean isValidSource( T newSource ) {
		if ( newSource == null ) 
			return true;
		
		A destination = newSource.getDestination();
        return destination == null || destination.equals(getDelegator());
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
	public boolean isTerminal() {
		return true;
	}
}
