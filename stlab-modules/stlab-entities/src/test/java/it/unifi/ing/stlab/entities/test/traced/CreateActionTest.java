package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeCreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class CreateActionTest extends ActionTest {

	protected FakeCreateAction createAction;
	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		createAction = createAction();
		tracedEntity = tracedEntity();
	}

	@Test
	public void testDelete() {
		createAction.assignTarget( tracedEntity );
		createAction.delete();
		
		assertNull( tracedEntity.getOrigin() );
		verify( garbageCollector ).garbage( createAction );
	}
	
	@Test
	public void testAssignTarget() {
		createAction.assignTarget( tracedEntity );
	
		assertEquals( tracedEntity, createAction.getTarget() );
		assertEquals( createAction, tracedEntity.getOrigin() );
	}

	@Test
	public void testAssignTargetDouble() {
		createAction.assignTarget( tracedEntity );
		createAction.assignTarget( tracedEntity );
	
		assertEquals( tracedEntity, createAction.getTarget() );
		assertEquals( createAction, tracedEntity.getOrigin() );
	}
	
	@Test
	public void testAssignTargetNull() {
		createAction.assignTarget( tracedEntity );
		createAction.assignTarget( null );
		assertNull( tracedEntity.getOrigin() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignTargetUsed() {
		createAction.assignTarget( tracedEntity );
		createAction.assignTarget( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnCreate() {
		createAction().assignTarget( tracedEntity );
		createAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnModify() {
		modifyAction().assignTarget( tracedEntity );
		createAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit1() {
		splitAction().assignTarget1( tracedEntity );
		createAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit2() {
		splitAction().assignTarget2( tracedEntity );
		createAction.assignTarget( tracedEntity );
	}
	
}
