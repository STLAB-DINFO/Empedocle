package it.unifi.ing.stlab.reflection.impl.factory;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactCreateAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactDeleteAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactMergeAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactModifyAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactSplitAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.UUID;

public class FactActionFactory 
	extends AbstractActionFactory<FactImpl,FactAction,User,Time>{

	@Override
	protected FactAction createAction() {
		return new FactCreateAction( UUID.randomUUID().toString() );
	}

	@Override
	protected FactAction modifyAction() {
		return new FactModifyAction( UUID.randomUUID().toString() );
	}

	@Override
	protected FactAction mergeAction() {
		return new FactMergeAction( UUID.randomUUID().toString() );
	}

	@Override
	protected FactAction splitAction() {
		return new FactSplitAction( UUID.randomUUID().toString() );
	}

	@Override
	protected FactAction deleteAction() {
		return new FactDeleteAction( UUID.randomUUID().toString() );
	}
	
}
