package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MR" )
public class FactMergeAction
	extends FactAction 
	implements MergeAction<FactImpl, FactAction, User, Time> {

	

	public FactMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	protected FactMergeAction() {
		super();
		setDelegate( new MergeActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}

	@Transient
	protected MergeActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return (MergeActionImpl<FactImpl, FactAction, User, Time>)super.getDelegate();
	}
	
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public FactImpl getSource1() {
		return getDelegate().getSource1();
	}
	protected void setSource1(FactImpl source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(FactImpl newSource1) {
		getDelegate().assignSource1(newSource1);
	}

	
	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public FactImpl getSource2() {
		return getDelegate().getSource2();
	}
	protected void setSource2(FactImpl source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(FactImpl newSource2) {
		getDelegate().assignSource2(newSource2);
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
