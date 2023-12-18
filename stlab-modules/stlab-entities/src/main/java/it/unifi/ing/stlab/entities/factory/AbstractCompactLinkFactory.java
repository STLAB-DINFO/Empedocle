package it.unifi.ing.stlab.entities.factory;

import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.compact.InsertLink;
import it.unifi.ing.stlab.entities.model.compact.RemoveLink;
import it.unifi.ing.stlab.entities.model.compact.UpdateLink;

public abstract class AbstractCompactLinkFactory 
	<T extends CompactEntity<T,L,?>, 
	L extends CompactLink<T,L>> { 

	
	@SuppressWarnings("unchecked")
	public L insertLink( T source, T target ) {
		InsertLink<T,L> result = (InsertLink<T, L>) insertLink();
		result.assignSource( source );
		result.assignTarget( target );
		return (L)result;
	}
	protected abstract L insertLink();
	
	
	@SuppressWarnings("unchecked")
	public L updateLink( T source, T target, L refersTo ) {
		UpdateLink<T,L> result = (UpdateLink<T, L>) updateLink();
		result.assignRefersTo( refersTo );
		result.assignSource( source );
		result.assignTarget( target );

		if( refersTo.getPriority() != null )
			result.setPriority( refersTo.getPriority() );
		
		return (L)result;
	}
	protected abstract L updateLink();
	
	
	@SuppressWarnings("unchecked")
	public L removeLink( T source, L refersTo ) {
		RemoveLink<T,L> result = (RemoveLink<T, L>) removeLink();
		result.assignSource( source );
		result.assignRefersTo( refersTo );
		return (L)result;
	}
	protected abstract L removeLink();

}
