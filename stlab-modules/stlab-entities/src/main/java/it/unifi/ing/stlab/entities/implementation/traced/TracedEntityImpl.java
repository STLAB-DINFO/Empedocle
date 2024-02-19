package it.unifi.ing.stlab.entities.implementation.traced;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TracedEntityImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,?,?>> 

	implements TracedEntity<T,A>, Delegated<T> {

	private T delegator;
	
	private Set<T> before;
	private Set<T> after;
	
	private A origin;
	private A destination;
	
	public TracedEntityImpl() {
		before = new HashSet<T>();
		after = new HashSet<T>();
	}
	
	
	@Override
	public T getDelegator() {
		return delegator;
	}
	@Override
	public void setDelegator(T delegator) {
		this.delegator = delegator;
	}

	

	@Override
	public void init() {
		before.add( delegator );
		after.add( delegator );
	}

	@Override
	public void delete() {
		if ( !isDeletable() ) throw new UnsupportedOperationException();

		if ( origin != null ) {
			origin.delete();
		}
		
		if ( destination != null ) {
			destination.delete();
		}
		
		GarbageCollector.getInstance().garbage( getDelegator());
	}
	
	private boolean isDeletable() {
		return ( origin == null || ClassHelper.instanceOf( origin, CreateAction.class )) &&
				( destination == null || ClassHelper.instanceOf( destination, DeleteAction.class ));
	}
	
	@Override
	public Set<T> listBefore() {
		return Collections.unmodifiableSet( before );
	}
	public Set<T> getBefore() {
		return before;
	}
	public void setBefore(Set<T> before) {
		this.before = before;
	}

	
	@Override
	public Set<T> listAfter() {
		return Collections.unmodifiableSet( after );
	}
	public Set<T> getAfter() {
		return after;
	}
	public void setAfter(Set<T> after) {
		this.after = after;
	}

	
	@Override
	public A getOrigin() {
		return origin;
	}
	public void setOrigin(A origin) {
		this.origin = origin;
	}


	@Override
	public A getDestination() {
		return destination;
	}
	public void setDestination(A destination) {
		this.destination = destination;
	}

	//an entity is active if it has not a destination or its destination is terminal
	@Override
	public boolean isActive() {
		if ( getDestination() == null ) return true;
		
		return getDestination().isTerminal(); //example delete
	}


	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean sameAs(T entity) {
		throw new UnsupportedOperationException();
	}


	@Override
	public T copy() {
		throw new UnsupportedOperationException();
	}
}
