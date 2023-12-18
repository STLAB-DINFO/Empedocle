package it.unifi.ing.stlab.entities.implementation.compact;

import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.compact.RemoveLink;
import it.unifi.ing.stlab.entities.model.compact.UpdateLink;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

public class UpdateLinkImpl 
	<T extends CompactEntity<T,L,?>, 
	 L extends CompactLink<T,L>> 

	extends CompactLinkImpl<T,L> implements UpdateLink<T,L> {

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
	public void setRefersTo(L refersTo) {
		this.refersTo = refersTo;
	}
	@Override
	public void assignRefersTo( L newRefersTo ) {
		if ( !isValieRefersTo( newRefersTo )) 
			throw new IllegalArgumentException();
			
		refersTo = newRefersTo;
	}

	private boolean isValieRefersTo( L newRefersTo ) {
		return ( newRefersTo == null || !(ClassHelper.instanceOf( newRefersTo, RemoveLink.class ))) &&
				isValidLink( getSource(), newRefersTo, getTarget() );
	}
	
	
	// Source methods
	@Override
	public void assignSource(T newSource) {
		if ( !isValidSource( newSource ) )
			throw new IllegalArgumentException();
		
		super.assignSource( newSource );
	}

	private boolean isValidSource( T newSource ) {
		return isValidLink( newSource, getRefersTo(), getTarget() );
	}
	
	// Target methods
	@Override
	public void assignTarget(T newTarget ) {
		if ( !isValidTarget( newTarget ) )
			throw new IllegalArgumentException();
		
		super.assignTarget( newTarget );
	}
	
	private boolean isValidTarget( T newTarget ) {
		return isValidLink( getSource(), getRefersTo(), newTarget );
	}


	private boolean isValidLink( T source, L link, T target ) {
		return isLinkOfBefore( link, source ) &&
				isAfterOfBeforeChildren( target, source );
	}
	
	private boolean isLinkOfBefore( L link, T entity ) {
		if ( entity == null || link == null )
			return true;
		
		for ( T before : entity.listBefore() ) {
			if ( before.listChildren().contains( link ))
				return true;
		}
		return false;
	}
	
	private boolean isAfterOfBeforeChildren( T afterEntity, T beforeEntity ) {
		if ( beforeEntity == null || afterEntity == null ) return true;
		
		
		for ( T before : beforeEntity.listBefore() ) {
			for ( L link : before.listChildren() ) {
				if ( link.getTarget() != null &&
				     link.getTarget().listAfter().contains( afterEntity )) {
				     return true;
				}
			}
		}
		
		return false;
	}
	
}
