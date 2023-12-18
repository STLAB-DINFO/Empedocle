package it.unifi.ing.stlab.entities.model.composite;


import java.util.List;
import java.util.Set;

/**
 * Interfaccia che definisce un'entita' composta, ovvero una struttura a grafo
 * con figli e padri (possono essere molteplici anche i parents).
 * @see it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl
 * 
 * @param <T> estende CompositeEntity<T,E>
 * @param <E> estende CompositeLink<T,E>
 */
public interface CompositeEntity
	<T extends CompositeEntity<T,E>, 
	 E extends CompositeLink<T,E>> {

	public void init();
	public void delete();
	
	/**
	 * Metodo che restituisce l'insieme di tutti gli ancestors dell'entita',
	 * che sono a loro volta CompositeEntity, compresa essa stessa.
	 * @return Set<T>, dove T estende CompositeEntity
	 */
	public Set<T> listAncestors();
	/**
	 * Metodo che restituisce l'insieme di tutti i descendents dell'entita',
	 * che sono a loro volta CompositeEntity, compresa essa stessa.
	 * @return Set<T>, dove T estende CompositeEntity
	 */
	public Set<T> listDescendents();
	
	/**
	 * Metodo che restituisce l'insieme di link che collegano l'entita' 
	 * con le entita' padre.
	 * @return Set<E>, dove E estende CompositeLink
	 */
	public Set<E> listParents();
	/**
	 * Metodo che restituisce l'insieme di link che collegano l'entita' 
	 * con le entita' figlie. Tali link sono istanze di una classe che implementa
	 * CompositeLink.
	 * @return Set<E>, dove E estende CompositeLink
	 */
	public Set<E> listChildren();
	/**
	 * Metodo che restituisce una lista di Link, ordinata secondo la priorita'
	 * assegnata ad essi. Tali link sono istanze di una classe che implementa
	 * CompositeLink.
	 * Il metodo crea la lista ordinata solo se non e' stata creata precedentemente e la
	 * memorizza come attributo della classe.
	 * @return Set<E>, dove E estende CompositeLink
	 */
	public List<E> listChildrenOrdered();
	/**
	 * Metodo che setta a null la lista childrenOrdered, per costringere
	 * il metodo listChildren() a ricrearla. Da utlizzare dopo aver fatto cambiamenti
	 * nei figli.
	 */
	public void refreshChildrenOrdered();
	
}
