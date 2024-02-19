package it.unifi.ing.stlab.patients.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.CreateActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "CR" )
public class PatientCreateAction
	extends PatientAction
	implements CreateAction<Patient,PatientAction,User,Time> {

	public PatientCreateAction(String uuid) {
		super(uuid);
		setDelegate( new CreateActionImpl<Patient,PatientAction,User,Time>() ); 
		getDelegate().setDelegator( this );
	}
	protected PatientCreateAction() {
		super();
		setDelegate( new CreateActionImpl<Patient,PatientAction,User,Time>() ); 
		getDelegate().setDelegator( this );
	}

	@Transient
	public CreateActionImpl<Patient, PatientAction, User, Time> getDelegate() {
		return (CreateActionImpl<Patient, PatientAction, User, Time>)super.getDelegate();
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
