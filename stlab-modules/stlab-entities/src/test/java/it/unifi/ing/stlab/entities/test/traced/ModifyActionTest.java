package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeModifyAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class ModifyActionTest extends ActionTest {

	protected FakeModifyAction modifyAction;
	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		modifyAction = modifyAction();
		tracedEntity = tracedEntity();
	}
	
	@Test
	public void testDelete() {
		FakeTracedEntity source = tracedEntity();
		
		modifyAction.assignSource( source );
		modifyAction.assignTarget( tracedEntity );
	
		modifyAction.delete();
		
		assertNull( source.getDestination() );
		assertNull( tracedEntity().getOrigin() );
		
		verify( garbageCollector ).garbage( modifyAction );
	}
	
	@Test
	public void testAssignSource() {
		modifyAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, modifyAction.getSource() );
		assertEquals( modifyAction, tracedEntity.getDestination() );
	}

	@Test
	public void testAssignSourceDouble() {
		modifyAction.assignSource( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, modifyAction.getSource() );
		assertEquals( modifyAction, tracedEntity.getDestination() );
	}
	
	@Test
	public void testAssignSourceNull() {
		modifyAction.assignSource( tracedEntity );
		modifyAction.assignSource( null );
		assertNull( tracedEntity.getDestination() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignSourceUsed() {
		modifyAction.assignSource( tracedEntity );
		modifyAction.assignSource( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnModify() {
		modifyAction().assignSource( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnDelete() {
		deleteAction().assignSource( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge1() {
		mergeAction().assignSource1( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge2() {
		mergeAction().assignSource2( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnSplit() {
		splitAction().assignSource( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}

	
	@Test
	public void testAssignTarget() {
		modifyAction.assignTarget( tracedEntity );
	
		assertEquals( tracedEntity, modifyAction.getTarget() );
		assertEquals( modifyAction, tracedEntity.getOrigin() );
	}

	@Test
	public void testAssignTargetDouble() {
		modifyAction.assignTarget( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	
		assertEquals( tracedEntity, modifyAction.getTarget() );
		assertEquals( modifyAction, tracedEntity.getOrigin() );
	}
	
	@Test
	public void testAssignTargetNull() {
		modifyAction.assignTarget( tracedEntity );
		modifyAction.assignTarget( null );

		assertNull( tracedEntity.getOrigin() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignTargetUsed() {
		modifyAction.assignTarget( tracedEntity );
		modifyAction.assignTarget( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnCreate() {
		createAction().assignTarget( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnModify() {
		modifyAction().assignTarget( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnMerge() {
		mergeAction().assignTarget( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit1() {
		splitAction().assignTarget1( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit2() {
		splitAction().assignTarget2( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}


	@Test
	public void testBefore() {
		FakeTracedEntity other = tracedEntity();

		modifyAction.assignSource( tracedEntity );
		modifyAction.assignTarget( other );
		
		assertEquals( 1, tracedEntity.getBefore().size() );
		assertTrue( tracedEntity.getBefore().contains( tracedEntity ));

		assertEquals( 2, other.getBefore().size() );
		assertTrue( other.getBefore().contains( other ));
		assertTrue( other.getBefore().contains( tracedEntity ));
	}

	@Test
	public void testAfter() {
		FakeTracedEntity other = tracedEntity();
		
		modifyAction.assignSource( tracedEntity );
		modifyAction.assignTarget( other );
		
		assertEquals( 2, tracedEntity.getAfter().size() );
		assertTrue( tracedEntity.getAfter().contains( tracedEntity ));
		assertTrue( tracedEntity.getAfter().contains( other ));

		assertEquals( 1, other.getAfter().size() );
		assertTrue( other.getAfter().contains( other ));
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid1() {
		modifyAction.assignSource( tracedEntity );
		modifyAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid2() {
		modifyAction.assignTarget( tracedEntity );
		modifyAction.assignSource( tracedEntity );
	}
}
