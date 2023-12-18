package it.unifi.ing.stlab.entities.test.compact;

import static org.mockito.Mockito.mock;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntityManager;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

import org.junit.Before;

public abstract class CompactEntityDaoTest {

	protected FakeCompactEntityManager compactEntityDao;
	protected FakeCompactEntity compactEntity;
	protected GarbageCollector garbageCollector;
	
	@Before
	public void setUp() {
		compactEntityDao = new FakeCompactEntityManager();
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
