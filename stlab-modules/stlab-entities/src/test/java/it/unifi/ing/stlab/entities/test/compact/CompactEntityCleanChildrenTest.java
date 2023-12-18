package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeInsertLink;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeRemoveLink;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeUpdateLink;

import org.junit.Test;

public class CompactEntityCleanChildrenTest extends CompactEntityDaoTest {

	protected FakeCompactEntity first, second, third, fourth, cleaned;
	protected FakeInsertLink insertLink;
	protected FakeUpdateLink updateLink;
	
	@Override
	public void setUp() {
		super.setUp();

		first = compactEntityDao.create( author(), time());
		first.setPayload( "first" );
		
		second = compactEntityDao.modify( author(), time(), first);

		third = compactEntityDao.create( author(), time());
		third.setPayload( "third" );
		
		fourth = compactEntityDao.modify( author(), time(), third );
		
		insertLink = new FakeInsertLink();
		insertLink.assignSource( first );
		insertLink.assignTarget( third );
		
		updateLink = new FakeUpdateLink();
		updateLink.assignSource( second );
		updateLink.assignTarget( fourth );
		updateLink.assignRefersTo( insertLink );
	}
	
	@Test
	public void testChildOnly() {
		fourth.setPayload( "" );
		
		cleaned = compactEntityDao.clean( second );
		
		assertEquals( second, cleaned );
		verify( garbageCollector, atLeast( 1 ) ).garbage( fourth );
		verify( garbageCollector ).garbage( updateLink );
	}

	@Test
	public void testAll() {
		second.setPayload( "" );
		fourth.setPayload( "" );
		
		cleaned = compactEntityDao.clean( second );
		
		assertNotNull( cleaned );
		verify( garbageCollector, atLeast( 1 ) ).garbage( fourth );
		verify( garbageCollector, never() ).garbage( second );
		verify( garbageCollector ).garbage( updateLink );
		assertEquals(1, second.listChildren().size());
		assertEquals(FakeRemoveLink.class, second.listChildren().iterator().next().getClass());
		assertEquals(insertLink, ((FakeRemoveLink)second.listChildren().iterator().next()).getRefersTo() );
	}

	@Test
	public void testNone() {
		second.setPayload( "" );
		cleaned = compactEntityDao.clean( second );
		
		assertEquals( second, cleaned );
		verify( garbageCollector, never() ).garbage( second );
		verify( garbageCollector, never() ).garbage( fourth );
		verify( garbageCollector, never() ).garbage( updateLink );
	}
}
