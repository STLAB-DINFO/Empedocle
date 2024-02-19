package it.unifi.ing.stlab.reflection.lite.model.facts;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeEntityImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.reflection.lite.model.facts.links.FactLinkLite;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.types.Type;

import java.util.*;

public abstract class FactLite 
		implements Fact, CompositeEntity<FactLite, FactLinkLite> {

	private final CompositeEntityImpl<FactLite, FactLinkLite> delegate;
	private List<FactLinkLite> childrenOrdered;
	private Type type;
	private FactStatus status;
	private FactContext context;
	
	public FactLite() {
		delegate = new CompositeEntityImpl<FactLite, FactLinkLite>();
		delegate.setDelegator( this );
		refreshChildrenOrdered();
	}

	//
	// Accessor Methods
	//
	
	// Ancestors
	protected Set<FactLite> getAncestors() {
		return delegate.getAncestors();
	}
	public Set<FactLite> listAncestors() {
		return delegate.listAncestors();
	}


	// Descendents
	protected Set<FactLite> getDescendents() {
		return delegate.getDescendents();
	}
	public Set<FactLite> listDescendents() {
		return delegate.listDescendents();
	}
	
	
	// Parents
	protected Set<FactLinkLite> getParents() {
		return delegate.getParents();
	}
	public Set<FactLinkLite> listParents() {
		return delegate.listParents();
	}

	
	// Children
	protected Set<FactLinkLite> getChildren() {
		return delegate.getChildren();
	}
	public Set<FactLinkLite> listChildren() {
		return delegate.listChildren();
	}
	public Set<FactLinkLite> listActiveLinks() {
		// in questo caso gli activeLinks coincidono con tutti i children
		return delegate.listChildren();
	}
	public List<FactLinkLite> listChildrenOrdered() {
		if ( childrenOrdered == null ) {
			initChildrenOrdered();
		}
		return childrenOrdered;
	}
	private void initChildrenOrdered() {
		childrenOrdered = new ArrayList<FactLinkLite>( listActiveLinks() );
		
		Collections.sort( childrenOrdered, new Comparator<FactLinkLite>() {

			@Override
			public int compare(FactLinkLite arg0, FactLinkLite arg1) {
				int p0 = priority( arg0 );
				int p1 = priority( arg1 );
				
				if( p0 == p1 && arg0.getPriority() != null && arg1.getPriority() != null )
					return arg0.getPriority().intValue() - arg1.getPriority().intValue();
				
				return p0 - p1;
			}
			private int priority(FactLinkLite link) {
				if ( link == null || 
					 link.getType() == null ||
					 link.getType().getPriority() == null ) return 0;

				return link.getType().getPriority().intValue();
			}
			
		});
	}
	public void refreshChildrenOrdered() {
		childrenOrdered = null;
	}
	
	
	//Type
	public Type getType() {
		return this.type;
	}
	protected void setType(Type type) {
		this.type = type;
	}

	
	// Context
	public FactContext getContext() {
		return context;
	}
	public void setContext(FactContext context) {
		this.context = context;
	}

	
	// Status
	public FactStatus getStatus() {
		return this.status;
	}
	public void setStatus(FactStatus status) {
		this.status = status;
	}
	
	
	
	//
	// Methods
	//
	
	public void init() {
		delegate.init();
	}

	
	public void delete() {
		delegate.delete();
	}
	
	
}
