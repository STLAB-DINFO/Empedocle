package it.unifi.ing.stlab.reflection.impl.model.facts.links;

import it.unifi.ing.stlab.entities.implementation.compact.UpdateLinkImpl;
import it.unifi.ing.stlab.entities.model.compact.UpdateLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "UP" )
public class FactUpdateLink 
	extends FactLinkImpl 
	implements UpdateLink<FactImpl, FactLinkImpl> {

	public FactUpdateLink(String uuid) {
		super(uuid);
		setDelegate( new UpdateLinkImpl<FactImpl, FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	protected FactUpdateLink() {
		super();
		setDelegate( new UpdateLinkImpl<FactImpl, FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected UpdateLinkImpl<FactImpl, FactLinkImpl> getDelegate() {
		return (UpdateLinkImpl<FactImpl, FactLinkImpl>)super.getDelegate();
	}
	
	
	@Override
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
