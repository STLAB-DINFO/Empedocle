package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeMergeAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class MergeActionTest extends ActionTest {

	protected FakeMergeAction mergeAction;
	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		mergeAction = mergeAction();
		tracedEntity = tracedEntity();
	}

	@Test
	public void testDelete() {
		FakeTracedEntity source1 = tracedEntity();
		FakeTracedEntity source2 = tracedEntity();
		
		mergeAction.assignSource1( source1 );
		mergeAction.assignSource2( source2 );
		mergeAction.assignTarget( tracedEntity );

		mergeAction.delete();
		
		assertNull( source1.getDestination() );
		assertNull( source2.getDestination() );
		assertNull( tracedEntity.getOrigin() );
		
		verify( garbageCollector ).garbage( mergeAction );
	}
	

	@Test
	public void testAssignSource1() {
		mergeAction.assignSource1( tracedEntity );
	
		assertEquals( tracedEntity, mergeAction.getSource1() );
		assertEquals( mergeAction, tracedEntity.getDestination() );
	}

	@Test
	public void testAssignSource1Double() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	
		assertEquals( tracedEntity, mergeAction.getSource1() );
		assertEquals( mergeAction, tracedEntity.getDestination() );
	}
	
	@Test
	public void testAssignSource1Null() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource1( null );

		assertNull( tracedEntity.getDestination() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignSource1Used() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource1( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource1OnModify() {
		modifyAction().assignSource( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource1OnDelete() {
		deleteAction().assignSource( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource1OnMerge() {
		mergeAction().assignSource1( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource1OnSplit() {
		splitAction().assignSource( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}

	
	@Test
	public void testAssignSource2() {
		mergeAction.assignSource2( tracedEntity );
	
		assertEquals( tracedEntity, mergeAction.getSource2() );
		assertEquals( mergeAction, tracedEntity.getDestination() );
	}

	@Test
	public void testAssignSource2Double() {
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	
		assertEquals( tracedEntity, mergeAction.getSource2() );
		assertEquals( mergeAction, tracedEntity.getDestination() );
	}
	
	@Test
	public void testAssignSource2Null() {
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignSource2( null );

		assertNull( tracedEntity.getDestination() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignSource2Used() {
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignSource2( tracedEntity() );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource2OnModify() {
		modifyAction().assignSource( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource2OnDelete() {
		deleteAction().assignSource( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource2OnMerge() {
		mergeAction().assignSource2( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSource2OnSplit() {
		splitAction().assignSource( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}

	
	@Test
	public void testAssignTarget() {
		mergeAction.assignTarget( tracedEntity );
	
		assertEquals( tracedEntity, mergeAction.getTarget() );
		assertEquals( mergeAction, tracedEntity.getOrigin() );
	}

	@Test
	public void testAssignTargetNull() {
		mergeAction.assignTarget( tracedEntity );
		mergeAction.assignTarget( null );

		assertNull( tracedEntity.getOrigin() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignTargetUsed() {
		mergeAction.assignTarget( tracedEntity );
		mergeAction.assignTarget( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnCreate() {
		createAction().assignTarget( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnModify() {
		modifyAction().assignTarget( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnMerge() {
		mergeAction().assignTarget( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit1() {
		splitAction().assignTarget1( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignTargetOnSplit2() {
		splitAction().assignTarget2( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}

	@Test
	public void testBefore() {
		FakeTracedEntity other1 = tracedEntity();
		FakeTracedEntity other2 = tracedEntity();
		
		mergeAction.assignSource1( other1 );
		mergeAction.assignSource2( other2 );
		mergeAction.assignTarget( tracedEntity );
		
		assertEquals( 3, tracedEntity.getBefore().size() );
		assertTrue( tracedEntity.getBefore().contains( tracedEntity ));
		assertTrue( tracedEntity.getBefore().contains( other1 ));
		assertTrue( tracedEntity.getBefore().contains( other2 ));

		assertEquals( 1, other1.getBefore().size() );
		assertTrue( other1.getBefore().contains( other1 ));

		assertEquals( 1, other2.getBefore().size() );
		assertTrue( other2.getBefore().contains( other2 ));
	}

	@Test
	public void testAfter() {
		FakeTracedEntity other1 = tracedEntity();
		FakeTracedEntity other2 = tracedEntity();
		
		mergeAction.assignSource1( other1 );
		mergeAction.assignSource2( other2 );
		mergeAction.assignTarget( tracedEntity );
		
		assertEquals( 1, tracedEntity.getAfter().size() );
		assertTrue( tracedEntity.getAfter().contains( tracedEntity ));

		assertEquals( 2, other1.getAfter().size() );
		assertTrue( other1.getAfter().contains( other1 ));
		assertTrue( other1.getAfter().contains( tracedEntity ));

		assertEquals( 2, other1.getAfter().size() );
		assertTrue( other2.getAfter().contains( other2 ));
		assertTrue( other2.getAfter().contains( tracedEntity ));
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid1() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid2() {
		mergeAction.assignTarget( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid3() {
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignTarget( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid4() {
		mergeAction.assignTarget( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid5() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource2( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignInvalid6() {
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignSource1( tracedEntity );
	}
	
	@Test
	public void testAssignNull() {
		mergeAction.assignSource1( null );
		mergeAction.assignSource2( null );
		mergeAction.assignTarget( tracedEntity );
	}
	
	@Test
	public void testAssignNull2() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource2( null );
		mergeAction.assignTarget( tracedEntity() );
	}
	
	@Test
	public void testAssignNull3() {
		mergeAction.assignSource1( null );
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignTarget( tracedEntity() );
	}
	
	@Test
	public void testAssignNull4() {
		mergeAction.assignSource1( null );
		mergeAction.assignSource2( null );
		mergeAction.assignTarget( null );
	}
	
	@Test
	public void testAssignNull5() {
		mergeAction.assignSource1( null );
		mergeAction.assignSource2( tracedEntity );
		mergeAction.assignTarget( null );
	}
	
	@Test
	public void testAssignNull6() {
		mergeAction.assignSource1( tracedEntity );
		mergeAction.assignSource2( null );
		mergeAction.assignTarget( null );
	}
}
