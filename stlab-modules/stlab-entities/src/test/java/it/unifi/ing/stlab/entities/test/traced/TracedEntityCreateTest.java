package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class TracedEntityCreateTest extends TracedEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testCreate() {
		tracedEntity = tracedEntityDao.create( new FakeActor(), new FakeTime() );
		
		assertNotNull( tracedEntity );
		assertTrue( ClassHelper.instanceOf( tracedEntity.getOrigin(), CreateAction.class ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCreateNoAuthor() {
		tracedEntity = tracedEntityDao.create( null, new FakeTime() );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCreateNoTime() {
		tracedEntity = tracedEntityDao.create( new FakeActor(), null );
	}
}
