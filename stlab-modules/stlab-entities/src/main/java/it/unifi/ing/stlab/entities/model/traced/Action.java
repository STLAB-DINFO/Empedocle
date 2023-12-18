package it.unifi.ing.stlab.entities.model.traced;

import it.unifi.ing.stlab.entities.model.timed.Time;

/**
 * Interfaccia che identifica l'azione che e' stata fatta su una (o piu') TracedEntity,
 * che ha portato alla creazione di una sua nuova versione (o piu'). <br />
 * E' caratterizzata da un autore, un tempo, e un flag che indica se e' un'azione
 * di tipo terminale.
 * Alcune possibili azioni sono Creation, Update, Merge o Split (vedere implementazione
 * specifica).
 * @see it.unifi.ing.stlab.entities.implementation.traced.ActionImpl
 *
 * @param <T> estende TracedEntity<T,A>
 * @param <A> estende Action<T,A,U,H>
 * @param <U> estende Actor, e' l'autore
 * @param <H> estende Time, e' il tempo in cui e' stata eseguita.
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
