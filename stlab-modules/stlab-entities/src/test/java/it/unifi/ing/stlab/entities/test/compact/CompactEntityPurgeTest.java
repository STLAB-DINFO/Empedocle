package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;

import org.junit.Test;

public class CompactEntityPurgeTest extends CompactEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testSimplePurge() {
		compactEntity = compactEntityDao.create( author(), time());

		assertEquals( compactEntity, compactEntityDao.purge( compactEntity ));
	}

	@Test
	public void testPurgeDeleted() {
		compactEntity = compactEntityDao.create( author(), time());
		compactEntity = compactEntityDao.delete( author(), time(), compactEntity);

		assertEquals( compactEntity, compactEntityDao.purge( compactEntity ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testPurgeNoEntity() {
		compactEntityDao.purge( null );
	}

	@Test
	public void testPurge() {
		FakeCompactEntity first = compactEntityDao.create( author(), time() );
		first.setPayload( "hello world" );
		FakeCompactEntity second = compactEntityDao.modify( author(), time(), first );
		FakeCompactAction modifyAction = second.getOrigin();
	
		assertNull( compactEntityDao.purge( second  ));
		assertTrue( first.isActive() );
		verify( garbageCollector ).garbage( modifyAction );
		verify( garbageCollector, atLeast( 1 ) ).garbage( second );
	}

	@Test
	public void testPurgeNoPurge() {
		FakeCompactEntity first = compactEntityDao.create( author(), time());
		first.setPayload( "hello world 1" );
		FakeCompactEntity second = compactEntityDao.modify( author(), time(), first);
		second.setPayload( "hello world 2" );
		
		assertEquals( second, compactEntityDao.purge( second ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testPurgeInactive() {
		FakeCompactEntity first = compactEntityDao.create( author(), time());
		
		compactEntityDao.modify( author(), time(), first);
		compactEntityDao.purge( first );
	}

}
