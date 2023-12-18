package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactActionFactory 
	extends AbstractActionFactory<FakeCompactEntity,FakeCompactAction, FakeActor, FakeTime> {

	@Override
	protected FakeCompactAction createAction() {
		return new FakeCompactCreateAction();
	}

	@Override
	protected FakeCompactAction modifyAction() {
		return new FakeCompactModifyAction();
	}

	@Override
	protected FakeCompactAction mergeAction() {
		return new FakeCompactMergeAction();
	}

	@Override
	protected FakeCompactAction splitAction() {
		return new FakeCompactSplitAction();
	}

	@Override
	protected FakeCompactAction deleteAction() {
		return new FakeCompactDeleteAction();
	}

}
