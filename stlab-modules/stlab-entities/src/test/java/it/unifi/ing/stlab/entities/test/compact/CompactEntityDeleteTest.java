package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class CompactEntityDeleteTest extends CompactEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
		compactEntity = compactEntityDao.create( new FakeActor(), new FakeTime() );
	}
	
	@Test
	public void testDelete() {
		compactEntity = compactEntityDao.delete( new FakeActor(), new FakeTime(), compactEntity );
		
		assertNotNull( compactEntity );
		assertTrue( ClassHelper.instanceOf( compactEntity.getOrigin(), CreateAction.class ));
		assertTrue( ClassHelper.instanceOf( compactEntity.getDestination(), DeleteAction.class ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoAuthor() {
		compactEntity = compactEntityDao.delete( null, new FakeTime(), compactEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoTime() {
		compactEntity = compactEntityDao.delete( new FakeActor(), null, compactEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testDeleteNoEntity() {
		compactEntity = compactEntityDao.delete( new FakeActor(), new FakeTime(), null );
	}
	
}
