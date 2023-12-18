package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "DL" )
public class FactDeleteAction
	extends FactAction 
	implements DeleteAction<FactImpl, FactAction, User, Time> {

		
	public FactDeleteAction(String uuid) {
		super(uuid);
		setDelegate( new DeleteActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	protected FactDeleteAction() {
		super();
		setDelegate( new DeleteActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}

	@Transient
	protected DeleteActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return (DeleteActionImpl<FactImpl, FactAction, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public FactImpl getSource() {
		return getDelegate().getSource();
	}
	public void setSource(FactImpl source) {
		getDelegate().setSource(source);
	}
	public void assignSource(FactImpl newSource) {
		getDelegate().assignSource(newSource);
	}

}
