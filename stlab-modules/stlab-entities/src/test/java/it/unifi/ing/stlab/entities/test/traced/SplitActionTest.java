package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeSplitAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class SplitActionTest extends ActionTest {

	protected FakeSplitAction splitAction;
	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		splitAction = splitAction();
		tracedEntity = tracedEntity();
	}
	
	@Test
	public void testDelete() {
		FakeTracedEntity target1 = tracedEntity();
		FakeTracedEntity target2 = tracedEntity();
		
		splitAction.assignSource( tracedEntity );
		splitAction.assignTarget1( target1 );
		splitAction.assignTarget2( target2 );
	
		splitAction.delete();
		
		assertNull( tracedEntity.getDestination() );
		assertNull( target1.getOrigin() );
		assertNull( target2.getOrigin() );
		
		verify( garbageCollector ).garbage( splitAction );
	}
	
	@Test
	public void testAssignSource() {
		splitAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getSource() );
		assertEquals( splitAction, tracedEntity.getDestination() );
	}

	@Test
	public void testAssignSourceDouble() {
		splitAction.assignSource( tracedEntity );
		splitAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getSource() );
		assertEquals( splitAction, tracedEntity.getDestination() );
	}
	
	@Test
	public void testAssignSourceNull() {
		splitAction.assignSource( tracedEntity );
		splitAction.assignSource( null );

		assertNull( tracedEntity.getDestination() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignSourceUsed() {
		splitAction.assignSource( tracedEntity );
		splitAction.assignSource( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnModify() {
		modifyAction().assignSource( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnDelete() {
		deleteAction().assignSource( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge1() {
		mergeAction().assignSource1( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge2() {
		mergeAction().assignSource2( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnSplit() {
		splitAction().assignSource( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	
	@Test
	public void testAssignTarget1() {
		splitAction.assignTarget1( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getTarget1() );
		assertEquals( splitAction, tracedEntity.getOrigin() );
	}

	@Test
	public void testAssignTarget1Double() {
		splitAction.assignTarget1( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getTarget1() );
		assertEquals( splitAction, tracedEntity.getOrigin() );
	}
	
	@Test
	public void testAssignTarget1Null() {
		splitAction.assignTarget1( tracedEntity );
		splitAction.assignTarget1( null );

		assertNull( tracedEntity.getOrigin() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignTarget1Used() {
		splitAction.assignTarget1( tracedEntity );
		splitAction.assignTarget1( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget1OnCreate() {
		createAction().assignTarget( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget1OnModify() {
		modifyAction().assignTarget( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget1OnMerge() {
		mergeAction().assignTarget( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget1OnSplit1() {
		splitAction().assignTarget1( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}

	@Test
	public void testAssignTarget2() {
		splitAction.assignTarget2( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getTarget2() );
		assertEquals( splitAction, tracedEntity.getOrigin() );
	}

	@Test
	public void testAssignTarget2Double() {
		splitAction.assignTarget2( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	
		assertEquals( tracedEntity, splitAction.getTarget2() );
		assertEquals( splitAction, tracedEntity.getOrigin() );
	}
	
	@Test
	public void testAssignTarget2Null() {
		splitAction.assignTarget2( tracedEntity );
		splitAction.assignTarget2( null );

		assertNull( tracedEntity.getOrigin() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignTarget2Used() {
		splitAction.assignTarget2( tracedEntity );
		splitAction.assignTarget2( tracedEntity() );
	}
	
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget2OnCreate() {
		createAction().assignTarget( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget2OnModify() {
		modifyAction().assignTarget( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget2OnMerge() {
		mergeAction().assignTarget( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTarget2OnSplit1() {
		splitAction().assignTarget2( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}
	
	@Test
	public void testBefore() {
		FakeTracedEntity other1 = tracedEntity();
		FakeTracedEntity other2 = tracedEntity();

		splitAction.assignSource( tracedEntity );
		splitAction.assignTarget1( other1  );
		splitAction.assignTarget2( other2 );
		
		assertEquals( 1, tracedEntity.getBefore().size() );
		assertTrue( tracedEntity.getBefore().contains( tracedEntity ));

		assertEquals( 2, other1.getBefore().size() );
		assertTrue( other1.getBefore().contains( other1 ));
		assertTrue( other1.getBefore().contains( tracedEntity ));

		assertEquals( 2, other2.getBefore().size() );
		assertTrue( other2.getBefore().contains( other2 ));
		assertTrue( other2.getBefore().contains( tracedEntity ));
	}

	@Test
	public void testAfter() {
		FakeTracedEntity other1 = tracedEntity();
		FakeTracedEntity other2 = tracedEntity();
		
		splitAction.assignSource( tracedEntity );
		splitAction.assignTarget1( other1  );
		splitAction.assignTarget2( other2 );
		
		assertEquals( 3, tracedEntity.getAfter().size() );
		assertTrue( tracedEntity.getAfter().contains( tracedEntity ));
		assertTrue( tracedEntity.getAfter().contains( other1 ));
		assertTrue( tracedEntity.getAfter().contains( other2 ));

		assertEquals( 1, other1.getAfter().size() );
		assertTrue( other1.getAfter().contains( other1 ));

		assertEquals( 1, other1.getAfter().size() );
		assertTrue( other2.getAfter().contains( other2 ));
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid1() {
		splitAction.assignSource( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid2() {
		splitAction.assignTarget1( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid3() {
		splitAction.assignSource( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid4() {
		splitAction.assignTarget2( tracedEntity );
		splitAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid5() {
		splitAction.assignTarget1( tracedEntity );
		splitAction.assignTarget2( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid6() {
		splitAction.assignTarget2( tracedEntity );
		splitAction.assignTarget1( tracedEntity );
	}
}
