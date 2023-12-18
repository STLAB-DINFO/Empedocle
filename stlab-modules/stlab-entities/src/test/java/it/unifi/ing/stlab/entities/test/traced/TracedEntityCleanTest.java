package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Test;

public class TracedEntityCleanTest extends TracedEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testSimpleClean() {
		tracedEntity = tracedEntityDao.create( author(), time());
		FakeAction origin = tracedEntity.getOrigin();
		
		assertNull( tracedEntityDao.clean( tracedEntity ));
		verify( garbageCollector ).garbage( origin );
		verify( garbageCollector, atLeast( 1 ) ).garbage( tracedEntity );
	}

	@Test
	public void testSimpleCleanNoClean() {
		tracedEntity = tracedEntityDao.create( author(), time());
		tracedEntity.setPayload( "hello world" );
		
		assertEquals( tracedEntity, tracedEntityDao.clean( tracedEntity ));
	}
	
	@Test
	public void testCleanDeleted() {
		tracedEntity = tracedEntityDao.create( author(), time());
		tracedEntity = tracedEntityDao.delete( author(), time(), tracedEntity);

		assertEquals( tracedEntity, tracedEntityDao.clean( tracedEntity ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testCleanNoEntity() {
		tracedEntityDao.clean( null );
	}


	@Test
	public void testCleanNoClean() {
		FakeTracedEntity first = tracedEntityDao.create( author(), time());
		first.setPayload( "hello world 1" );
		FakeTracedEntity second = tracedEntityDao.modify( author(), time(), first);
		
		assertEquals( second, tracedEntityDao.clean( second ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCleanInactive() {
		FakeTracedEntity first = tracedEntityDao.create( author(), time());
		
		tracedEntityDao.modify( author(), time(), first);
		tracedEntityDao.clean( first );
	}

}
