package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class TracedEntityModifyTest extends TracedEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testModify() {
		FakeTracedEntity baseEntity = tracedEntityDao.create( author(), time() );
		baseEntity.setPayload( "hello world" );
		FakeTracedEntity modifiedEntity = tracedEntityDao.modify( author(), time(), baseEntity );
	
		assertTrue( modifiedEntity.sameAs( baseEntity ));
		assertFalse( modifiedEntity.equals( baseEntity ));

		assertTrue( ClassHelper.instanceOf( baseEntity.getDestination(), ModifyAction.class ));
		assertTrue( ClassHelper.instanceOf( modifiedEntity.getOrigin(), ModifyAction.class ));
		assertTrue( baseEntity.getDestination().equals( modifiedEntity.getOrigin() ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoAuthor() {
		FakeTracedEntity baseEntity = tracedEntityDao.create( author(), time() );
		tracedEntityDao.modify( null, time(), baseEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoTime() {
		FakeTracedEntity baseEntity = tracedEntityDao.create( author(), time() );
		tracedEntityDao.modify( author(), null, baseEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoEntity() {
		tracedEntityDao.modify( author(), time(), null );
	}

}
