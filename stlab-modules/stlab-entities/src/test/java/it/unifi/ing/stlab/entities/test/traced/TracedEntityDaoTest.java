package it.unifi.ing.stlab.entities.test.traced;

import static org.mockito.Mockito.mock;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntityDao;

import org.junit.Before;

public abstract class TracedEntityDaoTest {

	protected FakeTracedEntityDao tracedEntityDao;
	protected FakeTracedEntity tracedEntity;
	protected GarbageCollector garbageCollector;
	
	@Before
	public void setUp() {
		tracedEntityDao = new FakeTracedEntityDao();
		garbageCollector = mock( GarbageCollector.class );
		GarbageCollector.setInstance( garbageCollector );
	}
	
	protected FakeActor author() { 
		return new FakeActor();
	}
	
	protected FakeTime time() {
		return new FakeTime();
	}
	
}
