package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeInsertLink;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class CompactEntityModifyTest extends CompactEntityDaoTest {

	@Override
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void testModify() {
		FakeCompactEntity baseEntity = compactEntityDao.create( author(), time() );
		baseEntity.setPayload( "hello world" );
		FakeCompactEntity modifiedEntity = compactEntityDao.modify( author(), time(), baseEntity );
	
		assertTrue( modifiedEntity.sameAs( baseEntity ));
		assertFalse( modifiedEntity.equals( baseEntity ));

		assertTrue( ClassHelper.instanceOf( baseEntity.getDestination(), ModifyAction.class ));
		assertTrue( ClassHelper.instanceOf( modifiedEntity.getOrigin(), ModifyAction.class ));
		assertTrue( baseEntity.getDestination().equals( modifiedEntity.getOrigin() ));
	}

	@Test
	public void testModifyWithChildren() {
		FakeCompactEntity baseEntity = compactEntityDao.create( author(), time() );
		baseEntity.setPayload( "payload1" );
		
		FakeCompactEntity childEntity = compactEntityDao.create( author(), time() );
		childEntity.setPayload( "payload2" );
		
		FakeInsertLink insertLink = new FakeInsertLink();
		insertLink.assignSource( baseEntity );
		insertLink.assignTarget( childEntity );
		
		
		FakeCompactEntity modifiedEntity = compactEntityDao.modify( author(), time(), baseEntity );
	
		assertTrue( modifiedEntity.sameAs( baseEntity ));
		assertFalse( modifiedEntity.equals( baseEntity ));

		assertTrue( ClassHelper.instanceOf( baseEntity.getDestination(), ModifyAction.class ));
		assertTrue( ClassHelper.instanceOf( modifiedEntity.getOrigin(), ModifyAction.class ));
		assertTrue( baseEntity.getDestination().equals( modifiedEntity.getOrigin() ));
		
		assertTrue( modifiedEntity.getBefore().contains( baseEntity ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoAuthor() {
		FakeCompactEntity baseEntity = compactEntityDao.create( author(), time() );
		compactEntityDao.modify( null, time(), baseEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoTime() {
		FakeCompactEntity baseEntity = compactEntityDao.create( author(), time() );
		compactEntityDao.modify( author(), null, baseEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testModifyNoEntity() {
		compactEntityDao.modify( author(), time(), null );
	}

}
