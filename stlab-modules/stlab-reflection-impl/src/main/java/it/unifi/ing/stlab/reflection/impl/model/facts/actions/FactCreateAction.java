package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "CR" )
public class FactCreateAction 	
	extends FactAction 
	implements CreateAction<FactImpl, FactAction, User, Time> {

	public FactCreateAction(String uuid) {
		super(uuid);
		setDelegate( new CreateActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	protected FactCreateAction() {
		super();
		setDelegate( new CreateActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}

	@Transient
	protected CreateActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return (CreateActionImpl<FactImpl, FactAction, User, Time>)super.getDelegate();
	}
	

	@ManyToOne
	@JoinColumn( name = "target_id" )
	public FactImpl getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(FactImpl target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(FactImpl newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
