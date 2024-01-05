package it.unifi.ing.stlab.entities.model.timed;

/**
 * Interfaccia che definisce la validita' temporale di un'entita'.
 * @see it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl
 *
 * @param <R> generic che estende TimeRange<H>, contenente due tempi
 * @param <H> generic che estende Time
 */
public interface TimedEntity<R extends TimeRange<H>, H extends Time> {

	/**
	 * Metodo che restituisce l'intervallo di tempo durante il quale 
	 * l'entita' e' considerata valida
	 * @return R che estende TimeRange<H>
	 */
    R getTimeRange();
	
	/**
	 * Metodo che controlla se l'intervallo di tempo di validita' della TimedEntity 
	 * passata come parametro contiene quello dell'entita' su cui e' invocato.
	 * @param timedEntity un'altra TimedEntity
	 * @return true se e' contenuto, false viceversa
	 */
    boolean isValidReference(TimedEntity<?, ?> timedEntity);
	
	/**
	 * Metodo che indica se questa entita' e' valida al tempo passato come parametro.
	 * @param time
	 * @return true se timeRange contiene time.
	 */
    boolean isValidAt(H time);

}
