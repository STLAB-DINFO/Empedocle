package it.unifi.ing.stlab.entities.test.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactCreateAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactDeleteAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactEntity;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactMergeAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactModifyAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeCompactSplitAction;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeInsertLink;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeRemoveLink;
import it.unifi.ing.stlab.entities.test.compact.fakes.FakeUpdateLink;

import org.junit.Before;
import org.junit.Test;

public class CompactEntityTest {

	protected GarbageCollector garbageCollector;
	protected FakeCompactEntity firstEntity;
	protected FakeCompactEntity secondEntity;
	protected FakeCompactEntity thirdEntity;
	protected FakeCompactEntity fourthEntity;
	
	
	protected FakeCompactEntity compactEntity() {
		FakeCompactEntity result = new FakeCompactEntity();
		result.init();
		return result;
	}
	
	protected FakeCompactCreateAction createAction() {
		return new FakeCompactCreateAction();
	}
	protected FakeCompactModifyAction modifyAction() {
		return new FakeCompactModifyAction();
	}
	protected FakeCompactMergeAction mergeAction() {
		return new FakeCompactMergeAction();
	}
	protected FakeCompactSplitAction splitAction() {
		return new FakeCompactSplitAction();
	}
	protected FakeCompactDeleteAction deleteAction() {
		return new FakeCompactDeleteAction();
	}
	
	protected FakeInsertLink insertLink() {
		return new FakeInsertLink();
	}
	protected FakeUpdateLink updateLink() {
		return new FakeUpdateLink();
	}
	protected FakeRemoveLink removeLink() {
		return new FakeRemoveLink();
	}
	
	@Before
	public void setUp() {
		firstEntity = compactEntity();
		secondEntity = compactEntity();
		thirdEntity = compactEntity();
		fourthEntity = compactEntity();

		createAction().assignTarget( firstEntity );
		
		FakeCompactModifyAction modifyAction = modifyAction();
		modifyAction.assignSource( firstEntity );
		modifyAction.assignTarget( secondEntity );
		
		garbageCollector = mock( GarbageCollector.class );
		GarbageCollector.setInstance( garbageCollector );
		
	}

	@Test
	public void testDelete0() {
		firstEntity = compactEntity();

		firstEntity.delete();

		verify( garbageCollector, atLeast( 1 )).garbage( firstEntity );
	}
	
	@Test
	public void testDelete1() {
		firstEntity = compactEntity();
		
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );
		
		firstEntity.delete();
		
		verify( garbageCollector, atLeast( 1 ) ).garbage( firstEntity );
		verify( garbageCollector, atLeast( 1 ) ).garbage( thirdEntity );
		verify( garbageCollector ).garbage( insertLink );
	}

	@Test
	public void testDelete2() {
		FakeCompactAction modifyAction = firstEntity.getDestination();
		
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );
		
		modifyAction.delete();
		secondEntity.delete();
		
		verify( garbageCollector, atLeast( 1 ) ).garbage( secondEntity );
		verify( garbageCollector ).garbage( modifyAction );
		verify( garbageCollector, never() ).garbage( firstEntity );
		verify( garbageCollector, never() ).garbage( thirdEntity );
		verify( garbageCollector, never() ).garbage( insertLink );
	}
	
	@Test
	public void testDelete3() {
		FakeCompactAction modifyAction1 = firstEntity.getDestination();

		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeCompactModifyAction modifyAction2 = modifyAction();
		modifyAction2.assignSource( thirdEntity );
		modifyAction2.assignTarget( fourthEntity );
		
		FakeUpdateLink updateLink = updateLink();
		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
		updateLink.assignRefersTo( insertLink );
		
		modifyAction1.delete();
		modifyAction2.delete();
		secondEntity.delete();
		
		verify( garbageCollector, never() ).garbage( firstEntity );
		verify( garbageCollector, never() ).garbage( thirdEntity );
		verify( garbageCollector, never() ).garbage( insertLink );
		verify( garbageCollector, atLeast( 1 ) ).garbage( secondEntity );
		verify( garbageCollector, atLeast( 1 ) ).garbage( fourthEntity );
		verify( garbageCollector ).garbage( updateLink );
	}
	
	@Test
	public void testDelete4() {
		FakeCompactAction modifyAction = firstEntity.getDestination();
		
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeRemoveLink removeLink = removeLink();
		removeLink.assignRefersTo( insertLink );
		removeLink.assignSource( secondEntity );
		
		modifyAction.delete();
		secondEntity.delete();
		
		verify( garbageCollector, never() ).garbage( firstEntity );
		verify( garbageCollector, never() ).garbage( thirdEntity );
		verify( garbageCollector, never() ).garbage( insertLink );
		verify( garbageCollector, atLeast( 1 )).garbage( secondEntity );
		verify( garbageCollector ).garbage( removeLink );
	}
	
	@Test
	public void testAddInsertLink() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );
		
		assertEquals( 1, firstEntity.getChildren().size() );
		assertEquals( 1, thirdEntity.getParents().size() );
	}

	@Test
	public void testRemoveInsertLink() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		insertLink.assignSource( null );
		insertLink.assignTarget( null );
		
		assertEquals( 0, firstEntity.getChildren().size() );
		assertEquals( 0, thirdEntity.getParents().size() );
	}

	@Test
	public void testAddUpdateLink() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeCompactModifyAction modifyAction = modifyAction();
		modifyAction.assignSource( thirdEntity );
		modifyAction.assignTarget( fourthEntity );
		
		FakeUpdateLink updateLink = updateLink();
		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
		updateLink.assignRefersTo( insertLink );
		
		assertEquals( 1, secondEntity.getChildren().size() );
		assertEquals( 1, fourthEntity.getParents().size() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testUpdateLinkBadReference1() {
		FakeRemoveLink removeLink = removeLink();

		FakeUpdateLink updateLink = updateLink();
		updateLink.assignRefersTo( removeLink );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testUpdateLinkBadReference2() {
		FakeInsertLink insertLink = insertLink();
		
		FakeUpdateLink updateLink = updateLink();
		updateLink.assignRefersTo( insertLink );

		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testUpdateLinkBadReference3() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeUpdateLink updateLink = updateLink();
		updateLink.assignRefersTo( insertLink );
		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
	}
	
	@Test
	public void testRemoveUpdateLink() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeCompactModifyAction modifyAction = modifyAction();
		modifyAction.assignSource( thirdEntity );
		modifyAction.assignTarget( fourthEntity );
		
		FakeUpdateLink updateLink = updateLink();
		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
		updateLink.assignRefersTo( insertLink );
		
		updateLink.assignSource( null );
		updateLink.assignTarget( null );
		
		assertEquals( 0, secondEntity.getChildren().size() );
		assertEquals( 0, fourthEntity.getParents().size() );
	}

	@Test
	public void testAddRemoveLink() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeRemoveLink removeLink = removeLink();
		removeLink.assignRefersTo( insertLink );
		removeLink.assignSource( secondEntity );
		
		assertEquals( 1, secondEntity.getChildren().size() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testRemoveLinkBadReference() {
		FakeRemoveLink other = removeLink();

		FakeRemoveLink removeLink = removeLink();
		removeLink.assignRefersTo( other );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testAddRemoveLinkFail() {
		FakeInsertLink insertLink = insertLink();

		FakeRemoveLink removeLink = removeLink();
		removeLink.assignRefersTo( insertLink );
		removeLink.assignSource( secondEntity );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testRemoveLinkUnsupportedOperation() {
		FakeRemoveLink removeLink = removeLink();
		removeLink.assignTarget( secondEntity );
	}

	@Test
	public void testActiveLinks1() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );
		
		assertEquals( 1, secondEntity.listActiveLinks().size() );
		assertTrue( secondEntity.listActiveLinks().contains( insertLink ));
	}

	@Test
	public void testActiveLinks2() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeCompactModifyAction modifyAction = modifyAction();
		modifyAction.assignSource( thirdEntity );
		modifyAction.assignTarget( fourthEntity );
		
		FakeUpdateLink updateLink = updateLink();
		updateLink.assignSource( secondEntity );
		updateLink.assignTarget( fourthEntity );
		updateLink.assignRefersTo( insertLink );
		
		assertEquals( 1, secondEntity.listActiveLinks().size() );
		assertTrue( secondEntity.listActiveLinks().contains( updateLink ));
	}

	@Test
	public void testActiveLinks3() {
		FakeInsertLink insertLink = insertLink();
		insertLink.assignSource( firstEntity );
		insertLink.assignTarget( thirdEntity );

		FakeRemoveLink removeLink = removeLink();
		removeLink.assignRefersTo( insertLink );
		removeLink.assignSource( secondEntity );
		
		assertEquals( 0, secondEntity.listActiveLinks().size() );
	}

}
