package it.unifi.ing.stlab.patients.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "SP" )
public class PatientSplitAction
	extends PatientAction
	implements SplitAction<Patient,PatientAction,User,Time> {

	public PatientSplitAction(String uuid) {
		super(uuid);
		setDelegate( new SplitActionImpl<Patient,PatientAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	protected PatientSplitAction() {
		super();
		setDelegate( new SplitActionImpl<Patient,PatientAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected SplitActionImpl<Patient,PatientAction,User,Time> getDelegate() {
		return (SplitActionImpl<Patient,PatientAction,User,Time>)super.getDelegate();
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
	@JoinColumn( name = "target1_id" )
	public Patient getTarget1() {
		return getDelegate().getTarget1();
	}
	protected void setTarget1(Patient target1) {
		getDelegate().setTarget1(target1);
	}
	public void assignTarget1(Patient newTarget1) {
		getDelegate().assignTarget1(newTarget1);
	}

	
	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public Patient getTarget2() {
		return getDelegate().getTarget2();
	}
	protected void setTarget2(Patient target2) {
		getDelegate().setTarget2(target2);
	}
	public void assignTarget2(Patient newTarget2) {
		getDelegate().assignTarget2(newTarget2);
	}

}
