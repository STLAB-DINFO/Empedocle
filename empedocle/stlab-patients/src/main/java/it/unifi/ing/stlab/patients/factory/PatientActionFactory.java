package it.unifi.ing.stlab.patients.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.actions.PatientAction;
import it.unifi.ing.stlab.patients.model.actions.PatientCreateAction;
import it.unifi.ing.stlab.patients.model.actions.PatientDeleteAction;
import it.unifi.ing.stlab.patients.model.actions.PatientMergeAction;
import it.unifi.ing.stlab.patients.model.actions.PatientModifyAction;
import it.unifi.ing.stlab.patients.model.actions.PatientSplitAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

public class PatientActionFactory extends AbstractActionFactory<Patient,PatientAction,User,Time>{

	@Override
	protected PatientAction createAction() {
		return new PatientCreateAction( UUID.randomUUID().toString() );
	}

	@Override
	protected PatientAction modifyAction() {
		return new PatientModifyAction( UUID.randomUUID().toString() );
	}

	@Override
	protected PatientAction mergeAction() {
		return new PatientMergeAction( UUID.randomUUID().toString() );
	}

	@Override
	protected PatientAction splitAction() {
		return new PatientSplitAction( UUID.randomUUID().toString() );
	}

	@Override
	protected PatientAction deleteAction() {
		return new PatientDeleteAction( UUID.randomUUID().toString() );
	}

}
