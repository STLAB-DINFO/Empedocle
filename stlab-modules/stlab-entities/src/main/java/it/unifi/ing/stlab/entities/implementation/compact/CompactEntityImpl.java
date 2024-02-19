package it.unifi.ing.stlab.entities.implementation.compact;


import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.compact.*;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompactEntityImpl 
	<T extends CompactEntity<T,L,A>,
	 L extends CompactLink<T,L>,
	 A extends Action<T,A,?,?>> 

	implements CompactEntity<T,L,A> {

	private T delegator;
	private final TracedEntityImpl<T,A> tracedEntity;
	private final CompositeEntityImpl<T,L> compositeEntity;
	
	public CompactEntityImpl() {
		tracedEntity = new TracedEntityImpl<T,A>();
		compositeEntity = new CompositeEntityImpl<T,L>();
	}
	
	public T getDelegator() {
		return delegator;
	}
	public void setDelegator(T delegator) {
		this.delegator = delegator;
		compositeEntity.setDelegator( delegator );
		tracedEntity.setDelegator( delegator );
	}
	
	
	@Override
	public void init() {
		compositeEntity.init();
		tracedEntity.init();
	}

	@Override
	public void delete() {
		compositeEntity.delete();
		tracedEntity.delete();
	}


	// Before methods
	public Set<T> listBefore() {
		return tracedEntity.listBefore();
	}
	public Set<T> getBefore() {
		return tracedEntity.getBefore();
	}
	public void setBefore(Set<T> before) {
		tracedEntity.setBefore(before);
	}

	

	// After methods
	public Set<T> listAfter() {
		return tracedEntity.listAfter();
	}
	public Set<T> getAfter() {
		return tracedEntity.getAfter();
	}
	public void setAfter(Set<T> after) {
		tracedEntity.setAfter(after);
	}

	
	// Origin methods
	public A getOrigin() {
		return tracedEntity.getOrigin();
	}
	public void setOrigin(A origin) {
		tracedEntity.setOrigin(origin);
	}

	
	// Destination methods
	public A getDestination() {
		return tracedEntity.getDestination();
	}
	public void setDestination(A destination) {
		tracedEntity.setDestination(destination);
	}

	

	// Ancestors methods
	public Set<T> listAncestors() {
		return compositeEntity.listAncestors();
	}
	public Set<T> getAncestors() {
		return compositeEntity.getAncestors();
	}
	public void setAncestors(Set<T> ancestors) {
		compositeEntity.setAncestors(ancestors);
	}

	

	// Descendents methods
	public Set<T> listDescendents() {
		return compositeEntity.listDescendents();
	}
	public Set<T> getDescendents() {
		return compositeEntity.getDescendents();
	}
	public void setDescendents(Set<T> descendents) {
		compositeEntity.setDescendents(descendents);
	}

	

	// Parents methos
	public Set<L> listParents() {
		return compositeEntity.listParents();
	}
	public Set<L> getParents() {
		return compositeEntity.getParents();
	}
	public void setParents(Set<L> parents) {
		compositeEntity.setParents(parents);
	}

	

	// Children methods
	public Set<L> listChildren() {
		return compositeEntity.listChildren();
	}
	public List<L> listChildrenOrdered() {
		return compositeEntity.listChildrenOrdered();
	}
	public void refreshChildrenOrdered() {
		compositeEntity.refreshChildrenOrdered();
	}
	public Set<L> getChildren() {
		return compositeEntity.getChildren();
	}
	public void setChildren(Set<L> children) {
		compositeEntity.setChildren(children);
	}

	
	
	public boolean isActive() {
		return tracedEntity.isActive();
	}

	
	//
	@Override
	public Set<L> listActiveLinks() {
		Set<CompactLink<T,L>> toIgnore = new HashSet<CompactLink<T,L>>();
		
		for ( L link : getRemoveLinks() ) {
			toIgnore.add( ((RemoveLink<T,L>)link).getRefersTo() );
		}
		for ( L link : getUpdateLinks() ) {
			toIgnore.add( ((UpdateLink<T,L>)link).getRefersTo() );
		}
		
		
		Set<L> result = new HashSet<L>();

		for ( L link : getInsertLinks() ) {
			if ( !toIgnore.contains( link )) {
				result.add( link );
			}
		}
		for ( L link : getUpdateLinks() ) {
			if ( !toIgnore.contains( link )) {
				result.add( link );
			}
		}
		
		return Collections.unmodifiableSet( result );
	}
	
	private Set<L> getInsertLinks() {
		Set<L> result = new HashSet<L>();
		
		for ( T before : getBefore() ) {
			for ( L link : before.listChildren() ) {
				if ( ClassHelper.instanceOf( link, InsertLink.class )) {
					result.add( link );
				}
			}
		}
		
		return result;
	}
	
	private Set<L> getUpdateLinks() {
		Set<L> result = new HashSet<L>();
		
		for ( T before : getBefore() ) {
			for ( L link : before.listChildren() ) {
				if ( ClassHelper.instanceOf( link, UpdateLink.class )) {
					result.add( link );
				}
			}
		}
		
		return result;
	}
	

	private Set<L> getRemoveLinks() {
		Set<L> result = new HashSet<L>();
		
		for ( T before : getBefore() ) {
			for ( L link : before.listChildren() ) {
				if ( ClassHelper.instanceOf( link, RemoveLink.class )) {
					result.add( link );
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean sameAs(T entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T copy() {
		throw new UnsupportedOperationException();
	}
}
