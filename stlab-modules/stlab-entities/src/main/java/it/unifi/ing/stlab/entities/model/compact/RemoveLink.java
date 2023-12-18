package it.unifi.ing.stlab.entities.model.compact;

public interface RemoveLink 
	<T extends CompactEntity<T,L,?>, 
	 L extends CompactLink<T,L>> 
	
	extends CompactLink<T,L> {

	public L getRefersTo();
	public void assignRefersTo( L refersTo );
}