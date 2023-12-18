package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Test;

public class TracedEntityPurgeTest extends TracedEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testSimplePurge() {
		tracedEntity = tracedEntityDao.create( author(), time());

		assertEquals( tracedEntity, tracedEntityDao.purge( tracedEntity ));
	}

	@Test
	public void testPurgeDeleted() {
		tracedEntity = tracedEntityDao.create( author(), time());
		tracedEntity = tracedEntityDao.delete( author(), time(), tracedEntity);

		assertEquals( tracedEntity, tracedEntityDao.purge( tracedEntity ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testPurgeNoEntity() {
		tracedEntityDao.purge( null );
	}

	@Test
	public void testPurge() {
		FakeTracedEntity first = tracedEntityDao.create( author(), time() );
		first.setPayload( "hello world" );
		FakeTracedEntity second = tracedEntityDao.modify( author(), time(), first );
		FakeAction modifyAction = second.getOrigin();
	
		assertNull( tracedEntityDao.purge( second  ));
		assertTrue( first.isActive() );
		verify( garbageCollector ).garbage( modifyAction );
		verify( garbageCollector, atLeast( 1 ) ).garbage( second );
	}

	@Test
	public void testPurgeNoPurge() {
		FakeTracedEntity first = tracedEntityDao.create( author(), time());
		first.setPayload( "hello world 1" );
		FakeTracedEntity second = tracedEntityDao.modify( author(), time(), first);
		second.setPayload( "hello world 2" );
		
		assertEquals( second, tracedEntityDao.purge( second ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testPurgeInactive() {
		FakeTracedEntity first = tracedEntityDao.create( author(), time());
		
		tracedEntityDao.modify( author(), time(), first);
		tracedEntityDao.purge( first );
	}

}
