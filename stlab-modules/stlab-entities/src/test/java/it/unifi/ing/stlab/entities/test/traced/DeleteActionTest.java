package it.unifi.ing.stlab.entities.test.traced;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeDeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class DeleteActionTest extends ActionTest {

	protected FakeDeleteAction deleteAction;
	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		deleteAction = deleteAction();
		tracedEntity = tracedEntity();
	}
	
	@Test
	public void testDelete() {
		deleteAction.assignSource( tracedEntity );
		deleteAction.delete();
		
		assertNull( tracedEntity.getDestination() );
		verify( garbageCollector ).garbage( deleteAction );
	}
	
	
	@Test
	public void testAssignSource() {
		deleteAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, deleteAction.getSource() );
		assertEquals( deleteAction, tracedEntity.getDestination() );
	}

	@Test
	public void testAssignSourceDouble() {
		deleteAction.assignSource( tracedEntity );
		deleteAction.assignSource( tracedEntity );
	
		assertEquals( tracedEntity, deleteAction.getSource() );
		assertEquals( deleteAction, tracedEntity.getDestination() );
	}
	
	@Test
	public void testAssignSourceNull() {
		deleteAction.assignSource( tracedEntity );
		deleteAction.assignSource( null );
		
		assertNull( tracedEntity.getDestination() );
	}
	
	@Test( expected = IllegalStateException.class )
	public void testAssignSourceUsed() {
		deleteAction.assignSource( tracedEntity );
		deleteAction.assignSource( tracedEntity() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnModify() {
		modifyAction().assignSource( tracedEntity );
		deleteAction.assignSource( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge1() {
		mergeAction().assignSource1( tracedEntity );
		deleteAction.assignSource( tracedEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnMerge2() {
		mergeAction().assignSource2( tracedEntity );
		deleteAction.assignSource( tracedEntity );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAssignSourceOnSplit() {
		splitAction().assignSource( tracedEntity );
		deleteAction.assignSource( tracedEntity );
	}
	
	
}

