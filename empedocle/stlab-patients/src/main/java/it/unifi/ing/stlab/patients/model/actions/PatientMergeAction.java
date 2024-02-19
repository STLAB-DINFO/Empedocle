package it.unifi.ing.stlab.patients.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.MergeActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.MergeAction;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MR" )
public class PatientMergeAction
	extends PatientAction
	implements MergeAction<Patient,PatientAction,User,Time> {

	public PatientMergeAction(String uuid) {
		super(uuid);
		setDelegate( new MergeActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected PatientMergeAction() {
		super();
		setDelegate( new MergeActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected MergeActionImpl<Patient, PatientAction, User, Time> getDelegate() {
		return (MergeActionImpl<Patient, PatientAction, User, Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source1_id" )
	public Patient getSource1() {
		return getDelegate().getSource1();
	}
	protected void setSource1(Patient source1) {
		getDelegate().setSource1(source1);
	}
	public void assignSource1(Patient newSource1) {
		getDelegate().assignSource1(newSource1);
	}

	
	@ManyToOne
	@JoinColumn( name = "source2_id" )
	public Patient getSource2() {
		return getDelegate().getSource2();
	}
	protected void setSource2(Patient source2) {
		getDelegate().setSource2(source2);
	}
	public void assignSource2(Patient newSource2) {
		getDelegate().assignSource2(newSource2);
	}

	
	@ManyToOne
	@JoinColumn( name = "target_id" )
	public Patient getTarget() {
		return getDelegate().getTarget();
	}
	protected void setTarget(Patient target) {
		getDelegate().setTarget(target);
	}
	public void assignTarget(Patient newTarget) {
		getDelegate().assignTarget(newTarget);
	}

}
