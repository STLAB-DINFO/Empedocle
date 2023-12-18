package it.unifi.ing.stlab.entities.model.compact;

public interface InsertLink 
	<T extends CompactEntity<T,L,?>, 
	 L extends CompactLink<T,L>> 
	
	extends CompactLink<T,L> {
}