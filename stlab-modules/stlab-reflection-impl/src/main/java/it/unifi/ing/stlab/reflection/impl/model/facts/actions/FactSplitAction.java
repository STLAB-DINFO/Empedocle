package it.unifi.ing.stlab.reflection.impl.model.facts.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "SP" )
public class FactSplitAction
	extends FactAction 
	implements SplitAction<FactImpl, FactAction, User, Time> {

	public FactSplitAction(String uuid) {
		super(uuid);
		setDelegate( new SplitActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	protected FactSplitAction() {
		super();
		setDelegate( new SplitActionImpl<FactImpl, FactAction, User, Time>() );
		getDelegate().setDelegator( this );
	}
	

	@Transient
	protected SplitActionImpl<FactImpl, FactAction, User, Time> getDelegate() {
		return (SplitActionImpl<FactImpl, FactAction, User, Time>)super.getDelegate();
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
	@JoinColumn( name = "target1_id" )
	public FactImpl getTarget1() {
		return getDelegate().getTarget1();
	}
	protected void setTarget1(FactImpl target1) {
		getDelegate().setTarget1(target1);
	}
	public void assignTarget1(FactImpl newTarget1) {
		getDelegate().assignTarget1(newTarget1);
	}

	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public FactImpl getTarget2() {
		return getDelegate().getTarget2();
	}
	protected void setTarget2(FactImpl target2) {
		getDelegate().setTarget2(target2);
	}
	public void assignTarget2(FactImpl newTarget2) {
		getDelegate().assignTarget2(newTarget2);
	}
	
}
