package it.unifi.ing.stlab.patients.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "DL" )
public class PatientDeleteAction
	extends PatientAction
	implements DeleteAction<Patient,PatientAction,User,Time> {

	public PatientDeleteAction(String uuid) {
		super(uuid);
		setDelegate( new DeleteActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	protected PatientDeleteAction() {
		super();
		setDelegate( new DeleteActionImpl<Patient,PatientAction,User,Time>() );
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected DeleteActionImpl<Patient, PatientAction, User, Time> getDelegate() {
		return (DeleteActionImpl<Patient,PatientAction,User,Time>)super.getDelegate();
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
}
