package it.unifi.ing.stlab.entities.model.compact;

import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;

import java.util.Set;


public interface CompactEntity 
	<T extends CompactEntity<T,L,A>,
	 L extends CompactLink<T,L>,
	 A extends Action<T,A,?,?>> 

	extends CompositeEntity<T,L>, TracedEntity<T,A> {
	
	Set<L> listActiveLinks();

}
