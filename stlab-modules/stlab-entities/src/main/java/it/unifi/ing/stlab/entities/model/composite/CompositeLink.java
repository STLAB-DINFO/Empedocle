package it.unifi.ing.stlab.entities.model.composite;


/**
 * Interface that defines the link between two instances of CompositeEntity,
 * as source and target, also assigning a priority to the link, used
 * in the ordering within the lists of Parents and Children. Usually,
 * the priority is the order of insertion in the tree.
 * @see it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl
 *
 * @param <T> generic that extends CompositeEntity<T,E>
 * @param <E> generic that extends CompositeLink<T,E>
 */

public interface CompositeLink
	<T extends CompositeEntity<T,E>, 
	 E extends CompositeLink<T,E>> {
	
	public void delete();

	public T getSource();
	/**
	 * Method that sets a source, inserting it in order
	 * in the lists of Ancestors, Descendants, and Children.
	 * @param source instance of CompositeEntity
	 */
	public void assignSource( T source );
	
	public T getTarget();
	/**
	 * Method that sets a Target, inserting it in order
	 * in the lists of Ancestors, Descendants, and Parents.
	 * @param target instance of CompositeEntity
	 */
	public void assignTarget( T target );

	public Long getPriority();
	public void setPriority( Long priority );
}
