package it.unifi.ing.stlab.entities.implementation.traced;

import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.utils.MethodHelper;

import java.lang.reflect.Method;
import java.util.Set;

public abstract class ActionImpl
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> 

	implements Action<T,A,U,H>, Delegated<A> {

	private A delegator;
	private U author;
	private H time;
	
	public ActionImpl() {
	}
	
	@Override
	public A getDelegator() {
		return delegator;
	}
	@Override
	public void setDelegator(A delegator) {
		this.delegator = delegator;
	}


	public U getAuthor() {
		return author;
	}
	public void setAuthor(U author) {
		this.author = author;
	}

	public H getTime() {
		return time;
	}
	public void setTime(H time) {
		this.time = time;
	}
	
	protected void setOrigin( T tracedEntity, A action ) {
		try {
			Method method = MethodHelper.getMethod( tracedEntity.getClass(), "setOrigin" );
			method.invoke( tracedEntity, action );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	protected void setDestination( T tracedEntity, A action ) {
		try {
			Method method = MethodHelper.getMethod( tracedEntity.getClass(), "setDestination" );
			method.invoke( tracedEntity, action );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	@SuppressWarnings("unchecked")
	protected Set<T> getBefore( T tracedEntity ) {
		try {
			Method method = MethodHelper.getMethod( tracedEntity.getClass(), "getBefore" );
			return (Set<T>) method.invoke( tracedEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Set<T> getAfter( T tracedEntity ) {
		try {
			Method method = MethodHelper.getMethod( tracedEntity.getClass(), "getAfter" );
			return (Set<T>) method.invoke( tracedEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
}
