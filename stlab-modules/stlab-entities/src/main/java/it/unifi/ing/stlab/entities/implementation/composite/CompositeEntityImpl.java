package it.unifi.ing.stlab.entities.implementation.composite;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeEntityImpl
	<T extends CompositeEntity<T,L>, 
	 L extends CompositeLink<T,L> > 

	implements CompositeEntity<T,L>, Delegated<T> {

	
	private T delegator;
	private Set<T> ancestors;
	private Set<T> descendents;
	private Set<L> parents;
	private Set<L> children;
	private List<L> childrenOrdered;

	public CompositeEntityImpl() {
		super();
		ancestors = new HashSet<T>();
		descendents = new HashSet<T>();
		parents = new HashSet<L>();
		children = new HashSet<L>();
		refreshChildrenOrdered();
	}
	
	
	@Override
	public T getDelegator() {
		return delegator;
	}
	@Override
	public void setDelegator(T delegator) {
		this.delegator = delegator;
	}


	@Override
	public void init() {
		ancestors.add( delegator );
		descendents.add( delegator );
	}
	
	

	@Override
	public void delete() {
		if ( !isDeletable() ) throw new UnsupportedOperationException();
		
		Set<CompositeLink<T,L>> children = new HashSet<CompositeLink<T,L>>();
		children.addAll( getChildren() );
		
		for ( CompositeLink<T,L> link : children ) {
			CompositeEntity<T,L> child = link.getTarget();
			
			link.delete();
			
			if ( child != null && child.listParents().size() == 0 ) {
				child.delete();
			}
		} 

		GarbageCollector.getInstance().garbage( getDelegator() );
	}

	private boolean isDeletable() {
		return getParents().size() <= 1;
	}
	
	@Override
	public Set<T> listAncestors() {
		return Collections.unmodifiableSet( ancestors );
	}
	public Set<T> getAncestors() {
		return ancestors;
	}
	public void setAncestors(Set<T> ancestors) {
		this.ancestors = ancestors;
	}

	@Override
	public Set<T> listDescendents() {
		return Collections.unmodifiableSet( descendents );
	}
	public Set<T> getDescendents() {
		return descendents;
	}
	public void setDescendents(Set<T> descendents) {
		this.descendents = descendents;
	}

	
	@Override
	public Set<L> listParents() {
		return Collections.unmodifiableSet( parents );
	}
	public Set<L> getParents() {
		return parents;
	}
	public void setParents(Set<L> parents) {
		this.parents = parents;
	}

	@Override
	public Set<L> listChildren() {
		return Collections.unmodifiableSet( children );
	}
	public Set<L> getChildren() {
		return children;
	}
	public void setChildren(Set<L> children) {
		this.children = children;
	}
	
	@Override
	public List<L> listChildrenOrdered() {
		if ( childrenOrdered == null ) {
			initChildrenOrdered();
		}
		return childrenOrdered;
	}

	private void initChildrenOrdered() {
		childrenOrdered = new ArrayList<L>( children );
		
		Collections.sort( childrenOrdered, new Comparator<L>() {

			@Override
			public int compare(L arg0, L arg1) {
				return priority( arg0 ) - priority( arg1 );
			}
			
			private int priority( L link ) {
				if ( link == null || link.getPriority() == null ) return 0;
				
				return link.getPriority().intValue();
			}
			
		});
	}
	
	@Override
	public void refreshChildrenOrdered() {
		childrenOrdered = null;
	}

}
