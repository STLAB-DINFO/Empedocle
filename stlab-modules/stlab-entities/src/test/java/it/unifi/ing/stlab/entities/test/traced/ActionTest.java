package it.unifi.ing.stlab.entities.test.traced;

import static org.mockito.Mockito.mock;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeCreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeDeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeMergeAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeModifyAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeSplitAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;


public class ActionTest {

	protected GarbageCollector garbageCollector;
	
	@Before
	public void setUp() {
		garbageCollector = mock( GarbageCollector.class );
		GarbageCollector.setInstance( garbageCollector );
	}
	
	protected FakeTracedEntity tracedEntity() {
		FakeTracedEntity result = new FakeTracedEntity();
		result.init();
		return result;
	}
	
	protected FakeCreateAction createAction() {
		return new FakeCreateAction();
	}

	protected FakeModifyAction modifyAction() {
		return new FakeModifyAction();
	}

	protected FakeMergeAction mergeAction() {
		return new FakeMergeAction();
	}
	
	protected FakeSplitAction splitAction() {
		return new FakeSplitAction();
	}
	
	protected FakeDeleteAction deleteAction() {
		return new FakeDeleteAction();
	}

}
