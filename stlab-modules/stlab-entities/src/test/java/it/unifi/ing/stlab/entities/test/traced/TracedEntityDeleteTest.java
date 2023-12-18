package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class TracedEntityDeleteTest extends TracedEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
		tracedEntity = tracedEntityDao.create( new FakeActor(), new FakeTime() );
	}
	
	@Test
	public void testDelete() {
		tracedEntity = tracedEntityDao.delete( new FakeActor(), new FakeTime(), tracedEntity );
		
		assertNotNull( tracedEntity );
		assertTrue( ClassHelper.instanceOf( tracedEntity.getOrigin(), CreateAction.class ));
		assertTrue( ClassHelper.instanceOf( tracedEntity.getDestination(), DeleteAction.class ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoAuthor() {
		tracedEntity = tracedEntityDao.delete( null, new FakeTime(), tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoTime() {
		tracedEntity = tracedEntityDao.delete( new FakeActor(), null, tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoEntity() {
		tracedEntity = tracedEntityDao.delete( new FakeActor(), new FakeTime(), null );
	}
	
}
