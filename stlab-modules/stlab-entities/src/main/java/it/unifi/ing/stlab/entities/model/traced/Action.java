package it.unifi.ing.stlab.entities.model.traced;

import it.unifi.ing.stlab.entities.model.timed.Time;

/**
 * Interface that identifies the action taken on one (or more) TracedEntity
 * that led to the creation of a new version (or versios) of it.
 * It is characterized by an author, a timestamp, and a flags indicating
 * Some possible actions are Creation, Update, Merge, Split (see specific implementation)
 * @see it.unifi.ing.stlab.entities.implementation.traced.ActionImpl
 *
 * @param <T> estende TracedEntity<T,A>
 * @param <A> estende Action<T,A,U,H>
 * @param <U> estende Actor, the author
 * @param <H> estende Time, timestamp in which the action has taken place.
 */
public interface Action
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time> {

	public void delete();
	
	public U getAuthor();
	public void setAuthor( U author );
	
	public H getTime();
	public void setTime( H time );

	public boolean isTerminal();  
	
}
