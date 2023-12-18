package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;

public class FakeActionFactory 
	extends AbstractActionFactory<FakeTracedEntity,FakeAction,FakeActor,FakeTime> {

	@Override
	protected FakeAction createAction() {
		return new FakeCreateAction();
	}

	@Override
	protected FakeAction modifyAction() {
		return new FakeModifyAction();
	}

	@Override
	protected FakeAction mergeAction() {
		return new FakeMergeAction();
	}

	@Override
	protected FakeAction splitAction() {
		return new FakeSplitAction();
	}

	@Override
	protected FakeAction deleteAction() {
		return new FakeDeleteAction();
	}

}
