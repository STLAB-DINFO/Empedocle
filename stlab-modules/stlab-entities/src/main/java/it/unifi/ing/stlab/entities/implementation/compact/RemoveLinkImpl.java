package it.unifi.ing.stlab.entities.implementation.compact;

import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.compact.RemoveLink;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

public class RemoveLinkImpl
	<T extends CompactEntity<T,L,?>, 
	L extends CompactLink<T,L>> 

	extends CompactLinkImpl<T,L> implements RemoveLink<T,L> {

	private L refersTo;

	@Override
	public void delete() {
		assignRefersTo( null );
		super.delete();
	}
	
	// RefersTo methods
	@Override
	public L getRefersTo() {
		return refersTo;
	}
	public void setRefersTo( L refersTo) {
		this.refersTo = refersTo;
	}
	@Override
	public void assignRefersTo( L newRefersTo ) {
		if ( !isValidRefersTo( newRefersTo )) 
			throw new IllegalArgumentException();
			
		refersTo = newRefersTo;
	}

	private boolean isValidRefersTo( L newRefersTo ) {
		return ( newRefersTo == null || !(ClassHelper.instanceOf( newRefersTo, RemoveLink.class ))) &&
			 isValidLink( getSource(), newRefersTo );
	}


	// Source methods
	@Override
	public void assignSource(T newSource) {
		if ( !isValidSource( newSource ) )
			throw new IllegalArgumentException();
		
		super.assignSource( newSource );
	}

	private boolean isValidSource( T newSource ) {
		return isValidLink( newSource, refersTo );
	}
	
	
	// Target methods
	@Override
	public void assignTarget(T target) {
		throw new UnsupportedOperationException();
	}

	
	private boolean isValidLink( T source, L link ) {
		if ( source == null || link == null ) return true;
		
		for ( T before : source.listBefore() ) {
			if ( before.listChildren().contains( link ))
				return true;
		}
		
		return false;
	}
	
}
