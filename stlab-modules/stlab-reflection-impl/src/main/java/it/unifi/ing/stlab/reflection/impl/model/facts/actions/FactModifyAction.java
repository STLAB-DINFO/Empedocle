package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MD" )
public class FactModifyAction 
	extends FactAction 
	implements ModifyAction<FactImpl, FactAction, User, Time> {

	public FactModifyAction(String uuid) {
		super(uuid);
		setDelegate( new ModifyActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	protected FactModifyAction() {
		super();
		setDelegate( new ModifyActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected ModifyActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return (ModifyActionImpl<FactImpl, FactAction, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public FactImpl getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(FactImpl source) {
		getDelegate().setSource(source);
	}
	public void assignSource(FactImpl newSource) {
		getDelegate().assignSource(newSource);
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
