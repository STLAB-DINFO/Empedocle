package it.unifi.ing.stlab.reflection.impl.model.facts.links;

import it.unifi.ing.stlab.entities.implementation.compact.InsertLinkImpl;
import it.unifi.ing.stlab.entities.model.compact.InsertLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "IN" )
public class FactInsertLink 
	extends FactLinkImpl 
	implements InsertLink<FactImpl,FactLinkImpl>  {

	public FactInsertLink(String uuid) {
		super(uuid);
		setDelegate( new InsertLinkImpl<FactImpl,FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	protected FactInsertLink() {
		super();
		setDelegate( new InsertLinkImpl<FactImpl,FactLinkImpl>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected InsertLinkImpl<FactImpl, FactLinkImpl> getDelegate() {
		return (InsertLinkImpl<FactImpl, FactLinkImpl>)super.getDelegate();
	}
	
}
