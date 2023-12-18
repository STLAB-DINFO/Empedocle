package it.unifi.ing.stlab.reflection.impl.model.facts.links;

import it.unifi.ing.stlab.entities.implementation.compact.RemoveLinkImpl;
import it.unifi.ing.stlab.entities.model.compact.RemoveLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "RM" )
public class FactRemoveLink 
	extends FactLinkImpl
	implements RemoveLink<FactImpl, FactLinkImpl> {


	public FactRemoveLink(String uuid) {
		super(uuid);
		setDelegate( new RemoveLinkImpl<FactImpl, FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	protected FactRemoveLink() {
		super();
		setDelegate( new RemoveLinkImpl<FactImpl, FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected RemoveLinkImpl<FactImpl, FactLinkImpl> getDelegate() {
		return (RemoveLinkImpl<FactImpl, FactLinkImpl>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "refers_to_id" )
	public FactLinkImpl getRefersTo() {
		return getDelegate().getRefersTo();
	}
	protected void setRefersTo(FactLinkImpl refersTo) {
		getDelegate().setRefersTo(refersTo);
	}
	public void assignRefersTo(FactLinkImpl newRefersTo) {
		getDelegate().assignRefersTo(newRefersTo);
		setType(newRefersTo.getType());
	}
	
}
