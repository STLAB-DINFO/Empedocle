package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.CreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class CompactEntityCreateTest extends CompactEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testCreate() {
		compactEntity = compactEntityDao.create( new FakeActor(), new FakeTime() );
		
		assertNotNull( compactEntity );
		assertTrue( ClassHelper.instanceOf( compactEntity.getOrigin(), CreateAction.class ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCreateNoAuthor() {
		compactEntity = compactEntityDao.create( null, new FakeTime() );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testCreateNoTime() {
		compactEntity = compactEntityDao.create( new FakeActor(), null );
	}
}
