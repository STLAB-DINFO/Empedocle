package it.unifi.ing.stlab.entities.model.traced;

import java.util.Set;

/**
 * Interfaccia che si occupa del versionamento delle entita' attraverso la definizione
 * di Before e After.
 * Ogni entita' e' collegata a una sua versione precedente attraverso una Action,
 * che specifica che tipo di modifica ha portato a quella nuova versione.
 * @see it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl
 *
 * @param <T> estende TracedEntity<T,A>
 * @param <A> estende l'interfaccia di Action
 */
public interface TracedEntity
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,?,?>> {

	void init();
	void delete();
	
	/**
	 * Metodo che restituisce la lista di tutte le versioni precedenti dell'entita'.
	 * @return Set<T>, dove T estende TracedEntity
	 */
    Set<T> listBefore();
	/**
	 * Metodo che restituisce la lista di tutte le versioni successive dell'entita'.
	 * @return Set<T>, dove T estende TracedEntity
	 */
    Set<T> listAfter();
	
	/**
	 * Metodo che restituisce la Action che collega la TracedEntity con la sua
	 * versione precedente.
	 * @return A che estende Action
	 */
    A getOrigin();
	/**
	 * Metodo che restituisce la Action che collega la TracedEntity con la sua
	 * versione successiva.
	 * @return A che estende Action
	 */
    A getDestination();
	
	boolean isActive();

	/**
	 * Metodo che controlla se l'entita' e' vuota (e' solo un metodo di default,
	 * la definizione di cosa vuol dire 'vuota' e' fatta localmente dai metodi che
	 * fanno l'override).
	 * @return true se e' vuota.
	 */
    boolean isEmpty();
	/**
	 * Metodo che controlla se l'entita' e' uguale a quella passata
	 * (e' solo un metodo di default, la definizione di cosa vuol dire 'uguale'
	 * e' fatta localmente dai metodi che fanno l'override).
	 * @return true se le entita' sono uguali.
	 */
    boolean sameAs(T entity);
	/**
	 * Metodo che restituisce una copia dell'entita' passata come parametro
	 * (e' solo un metodo di default, la definizione di come fare la copia
	 * e' fatta localmente dai metodi che fanno l'override).
	 * @return la TracedEntity copiata.
	 */
    T copy();
}
