package it.unifi.ing.stlab.view.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerTreeNode;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import org.junit.Before;
import org.junit.Test;

public class ViewerTreeNodeTest {

	protected ViewerTreeNode root;
	protected ViewerTreeNode child;
	protected ViewerTreeNode grandchild;
	protected ViewerLink link1;
	protected ViewerLink link2;
	protected ViewerLink link3;
	
	
	@Before
	public void setUp() {
		link1 = ViewerLinkFactory.createSubViewer();
		link2 = ViewerLinkFactory.createSubViewer();
		link3 = ViewerLinkFactory.createSubViewer();
		
		grandchild = new ViewerTreeNode( true, link3 );
		
		child = new ViewerTreeNode( false, link1 );
		child.addChild( link3.getUuid(), grandchild );
	
		root = new ViewerTreeNode( false, null );
		root.addChild( link1.getUuid(), child );
	}
	
	
	@Test
	public void testFind() {
		assertNotNull( root.find( link1.getUuid() ) );
		assertNull( root.find( link2.getUuid() ));
	}
	
	@Test
	public void testFindNull() {
		assertEquals( root, root.find( null ));
		assertNull( child.find( null ));
	}
	
	
	@Test
	public void testContains() {
		assertFalse( root.contains( root ));
		assertTrue( root.contains( child ));
	}
	
	@Test
	public void testParentOf() {
		assertNull( root.parentOf( root ));
		assertEquals( root, root.parentOf( child ));
	}
	
	@Test
	public void testDeepContains() {
		assertTrue( root.deepContains( root ));
		assertTrue( root.deepContains( child ));
		assertTrue( root.deepContains( grandchild ));
	
		assertFalse( child.deepContains( root ));
		assertTrue( child.deepContains( child ));
		assertTrue( child.deepContains( grandchild ));
	
		assertFalse( grandchild.deepContains( root ));
		assertFalse( grandchild.deepContains( child ));
		assertTrue( grandchild.deepContains( grandchild ));
	}
	
	@Test
	public void testAdjustChildrenPriority() {
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		ViewerLink link3 = ViewerLinkFactory.createSubViewer();
		
		ViewerTreeNode child1 = new ViewerTreeNode( false, link1 );
		ViewerTreeNode child2 = new ViewerTreeNode( false, link2 );
		ViewerTreeNode child3 = new ViewerTreeNode( false, link3 );
		
		ViewerTreeNode root = new ViewerTreeNode( false, null );
		root.addChild( link1.getUuid(), child1 );
		root.addChild( link2.getUuid(), child2 );
		root.addChild( link3.getUuid(), child3 );
		
		root.adjustChildrenPriority();
		
		assertEquals( new Long( 0 ), link1.getPriority() );
		assertEquals( new Long( 1 ), link2.getPriority() );
		assertEquals( new Long( 2), link3.getPriority() );
	}

	
	
	@Test
	public void testInsertAfter() {
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		ViewerLink link3 = ViewerLinkFactory.createSubViewer();
		
		ViewerTreeNode child1 = new ViewerTreeNode( false, link1 );
		ViewerTreeNode child2 = new ViewerTreeNode( false, link2 );
		ViewerTreeNode child3 = new ViewerTreeNode( false, link3 );
		
		ViewerTreeNode root = new ViewerTreeNode( false, null );
		root.addChild( link1.getUuid(), child1 );
		root.addChild( link3.getUuid(), child3 );

		root.insertAfter( child1, child2 );

		root.adjustChildrenPriority();
		
		assertEquals( new Long( 0 ), link1.getPriority() );
		assertEquals( new Long( 1 ), link2.getPriority() );
		assertEquals( new Long( 2), link3.getPriority() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testInsertAfterFail() {
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		
		ViewerTreeNode child1 = new ViewerTreeNode( false, link1 );
		ViewerTreeNode child2 = new ViewerTreeNode( false, link2 );
		
		ViewerTreeNode root = new ViewerTreeNode( false, null );
		root.insertAfter( child1, child2 );
	}
	
}
