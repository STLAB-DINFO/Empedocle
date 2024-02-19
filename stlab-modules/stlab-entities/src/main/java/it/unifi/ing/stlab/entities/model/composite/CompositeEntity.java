package it.unifi.ing.stlab.entities.model.composite;


import java.util.List;
import java.util.Set;

/**
 * Interface that defines a composite entity, i.e., a graph structure
 * with children and parents (there can also be multiple parents).
 * @see it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl
 * 
 * @param <T> extends CompositeEntity<T,E>
 * @param <E> extends CompositeLink<T,E>
 */
public interface CompositeEntity
	<T extends CompositeEntity<T,E>, 
	 E extends CompositeLink<T,E>> {

	void init();
	void delete();
	
	/**
	 * Metodo che restituisce l'insieme di tutti gli ancestors dell'entita',
	 * che sono a loro volta CompositeEntity, compresa essa stessa.
	 * @return Set<T>, dove T estende CompositeEntity
	 */
    Set<T> listAncestors();
	/**
	 * Method that returns the set of all the entity's ancestors,
	 * which are themselves CompositeEntity, including itself.
	 * @return Set<T>, where T extends CompositeEntity
	 */
    Set<T> listDescendents();
	
	/**
	 * Method that returns the set of links connecting the entity
	 * with its parent entities.
	 * @return Set<E>, where E extends CompositeLink
	 */
    Set<E> listParents();
	/**
	 * Method that returns the set of links connecting the entity
	 * with its child entities. These links are instances of a class that implements CompositeLink.
	 * @return Set<E>, where E extends CompositeLink
	 */
    Set<E> listChildren();
	/**
	 * Method that returns a list of Links, ordered by the priority
	 * assigned to them. These links are instances of a class that implements
	 * CompositeLink.
	 * The method creates the ordered list only if it has not been previously created and stores it
	 * as an attribute of the class.
	 * @return Set<E>, where E extends CompositeLink
	 */
    List<E> listChildrenOrdered();
	/**
	 * Method that sets the childrenOrdered list to null, to force
	 * the listChildren() method to recreate it. To be used after making changes
	 * in the children.
	 */
    void refreshChildrenOrdered();
	
}
