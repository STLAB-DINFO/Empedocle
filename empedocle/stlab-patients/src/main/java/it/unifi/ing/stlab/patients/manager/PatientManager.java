package it.unifi.ing.stlab.patients.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.manager.AbstractTracedEntityManager;
import it.unifi.ing.stlab.patients.factory.PatientActionFactory;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.actions.PatientAction;
import it.unifi.ing.stlab.patients.model.actions.PatientMergeAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public class PatientManager
	extends AbstractTracedEntityManager<Patient,PatientAction,User,Time>{

	@Override
	protected AbstractActionFactory<Patient, PatientAction, User, Time> getActionFactory() {
		return new PatientActionFactory();
	}

	public Patient createPatient( User author, Time time ) {
		return init( PatientFactory.createPatient(), author, time );
	}
	
	public Patient merge( User author, Time time, Patient master, Patient slave ) {
		return ((PatientMergeAction) getActionFactory()
				.mergeAction(author, time, master, slave, master.copy()))
				.getTarget();
	}	

}
