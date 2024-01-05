package it.unifi.ing.stlab.entities.model.composite;


/**
 * Interfaccia che definisce il link tra due istanze di CompositeEntity,
 * come source e target, attribuendo anche una priorita' al link, utilizzata
 * nell'ordinamento all'interno delle liste di Parents e Children. Solitamente
 * la priorita' e' l'ordine di inserimento nell'albero.
 * @see it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl
 *
 * @param <T> generic che estende CompositeEntity<T,E>
 * @param <E> generic che estende CompositeLink<T,E>
 */

public interface CompositeLink
	<T extends CompositeEntity<T,E>, 
	 E extends CompositeLink<T,E>> {
	
	void delete();

	T getSource();
	/**
	 * Metodo che effettua il set di un Target, inserendolo in ordine
	 * nelle liste di Ancestors, Descendents e Children.
	 * @param target istanza di CompositeEntity
	 */
    void assignSource(T source);
	
	T getTarget();
	/**
	 * Metodo che effettua il set di un Target, inserendolo in ordine
	 * nelle liste di Ancestors, Descendents e Parents.
	 * @param target istanza di CompositeEntity
	 */
    void assignTarget(T target);

	Long getPriority();
	void setPriority(Long priority);
}
