package it.unifi.ing.stlab.entities.model.traced;

import java.util.Set;

/**
 * Interface that handles the versioning of entities through the definition of Before and After.
 * Each entity is linked to its previous version through an action
 * that specifies what type of modification led to that new version
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
	 * Method that returns the list of all previous versions of the entity.
	 * @return Set<T>, dove T estende TracedEntity
	 */
    Set<T> listBefore();
	/**
	 * Method that returns the list of all subsequent versions of the entity
	 * @return Set<T>, where T extends TracedEntity
	 */
    Set<T> listAfter();
	
	/**
	 * Method that returns the Action that links the TracedEntity with its previous version
	 * @return A which extends Action
	 */
    A getOrigin();
	/**
	 * Method that returns the Action that links the TracedEntity with its subsequent version
	 * @return A which extends Action
	 */
    A getDestination();
	
	boolean isActive();

	/**
	 * Method that verifies whether the entity is empty
	 * (it is merely a default method, the definition of what constitutes 'empty'
	 * is determined locally by the methods that override it).
	 * @return true if it's empty.
	 */
    boolean isEmpty();
	/**
	 * Method that checks if the entity is equal to its previous version.
	 * (it is merely a default method, the definition of what constitutes 'equal'
	 * is determined locally by the methods that override it).
	 * @return true if the entities are equal.
	 */
    boolean sameAs(T entity);
	/**
	 * Method that return a copy of the entity passed as a parameter.
	 * (it is merely a default method, the definition of how to do the copy
	 * is done locally by the methods that override it).
	 * @return the copied TracedEntity.
	 */
    T copy();
}
