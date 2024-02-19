package it.unifi.ing.stlab.reflection.lite.model.facts.links;

import it.unifi.ing.stlab.entities.implementation.composite.CompositeLinkImpl;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.reflection.lite.model.facts.FactLite;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class FactLinkLite 
		implements FactLink, CompositeLink<FactLite,FactLinkLite> {

	private final CompositeLinkImpl<FactLite, FactLinkLite> delegate;
	private TypeLink type;
	
	public FactLinkLite() {
		delegate = new CompositeLinkImpl<FactLite, FactLinkLite>();
		delegate.setDelegator( this );
	}
	
	//
	// Accessor Methods
	//
	
	// Type
	public TypeLink getType() {
		return this.type;
	}
	public void setType(TypeLink type) {
		this.type = type;
	}
	
	
	// Source
	public FactLite getSource() {
		return delegate.getSource();
	}
	protected void setSource(FactLite source) {
		delegate.setSource( source );
	}
	public void assignSource(FactLite source) {
		delegate.assignSource( source );
	}

	
	// Target
	public FactLite getTarget() {
		return delegate.getTarget();
	}
	protected void setTarget(FactLite target) {
		delegate.setTarget( target );
	}
	public void assignTarget(FactLite target) {
		delegate.assignTarget( target );
		
	}
	
	
	// Priority
	public Long getPriority() {
		return delegate.getPriority();
	}
	public void setPriority(Long priority) {
		delegate.setPriority( priority );
	}

	
	//
	// Methods
	//
	
	public void delete() {
		delegate.delete();
	}
	

}
