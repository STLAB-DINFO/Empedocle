package it.unifi.ing.stlab.entities.model.compact;

import it.unifi.ing.stlab.entities.model.composite.CompositeLink;

public interface CompactLink
	<T extends CompactEntity<T,L,?>, 
	 L extends CompactLink<T,L>> 
	
	extends CompositeLink<T,L> {
}