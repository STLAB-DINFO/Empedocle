package it.unifi.ing.stlab.patients.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "MD" )
public class PatientModifyAction
	extends PatientAction
	implements ModifyAction<Patient,PatientAction,User,Time> {

	public PatientModifyAction(String uuid) {
		super(uuid);
		setDelegate( new ModifyActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected PatientModifyAction() {
		super();
		setDelegate( new ModifyActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected ModifyActionImpl<Patient,PatientAction,User,Time> getDelegate() {
		return (ModifyActionImpl<Patient,PatientAction,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public Patient getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(Patient source) {
		getDelegate().setSource(source);
	}
	public void assignSource(Patient newSource) {
		getDelegate().assignSource(newSource);
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
