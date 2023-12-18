package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;

import org.junit.Test;

public class CompactEntityCleanTest extends CompactEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testSimpleClean() {
		compactEntity = compactEntityDao.create( author(), time());
		FakeCompactAction origin = compactEntity.getOrigin();
		
		assertNull( compactEntityDao.clean( compactEntity ));
		verify( garbageCollector ).garbage( origin );
		verify( garbageCollector, atLeast( 1 ) ).garbage( compactEntity );
	}

	@Test
	public void testSimpleCleanNoClean() {
		compactEntity = compactEntityDao.create( author(), time());
		compactEntity.setPayload( "hello world" );
		
		assertEquals( compactEntity, compactEntityDao.clean( compactEntity ));
	}
	
	@Test
	public void testCleanDeleted() {
		compactEntity = compactEntityDao.create( author(), time());
		compactEntity = compactEntityDao.delete( author(), time(), compactEntity);

		assertEquals( compactEntity, compactEntityDao.clean( compactEntity ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testCleanNoEntity() {
		compactEntityDao.clean( null );
	}


	@Test
	public void testCleanNoClean() {
		FakeCompactEntity first = compactEntityDao.create( author(), time());
		first.setPayload( "hello world 1" );
		FakeCompactEntity second = compactEntityDao.modify( author(), time(), first);
		
		assertEquals( second, compactEntityDao.clean( second ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCleanInactive() {
		FakeCompactEntity first = compactEntityDao.create( author(), time());
		
		compactEntityDao.modify( author(), time(), first);
		compactEntityDao.clean( first );
	}

}
