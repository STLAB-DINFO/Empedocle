package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.actions.wrappers.TypeTreeNode;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Before;
import org.junit.Test;

public class TypeTreeNodeTest {

	protected TypeTreeNode root;
	protected TypeTreeNode child;
	protected TypeTreeNode grandchild;
	protected TypeLink link1;
	protected TypeLink link2;
	protected TypeLink link3;
	
	
	@Before
	public void setUp() {
		link1 = TypeLinkFactory.createLink();
		link2 = TypeLinkFactory.createLink();
		link3 = TypeLinkFactory.createLink();
		
		grandchild = new TypeTreeNode( true, link3 );
		
		child = new TypeTreeNode( false, link1 );
		child.addChild( link3.getUuid(), grandchild );
	
		root = new TypeTreeNode( false, null );
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
		TypeLink link1 = TypeLinkFactory.createLink();
		TypeLink link2 = TypeLinkFactory.createLink();
		TypeLink link3 = TypeLinkFactory.createLink();
		
		TypeTreeNode child1 = new TypeTreeNode( false, link1 );
		TypeTreeNode child2 = new TypeTreeNode( false, link2 );
		TypeTreeNode child3 = new TypeTreeNode( false, link3 );
		
		TypeTreeNode root = new TypeTreeNode( false, null );
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
		TypeLink link1 = TypeLinkFactory.createLink();
		TypeLink link2 = TypeLinkFactory.createLink();
		TypeLink link3 = TypeLinkFactory.createLink();
		
		TypeTreeNode child1 = new TypeTreeNode( false, link1 );
		TypeTreeNode child2 = new TypeTreeNode( false, link2 );
		TypeTreeNode child3 = new TypeTreeNode( false, link3 );
		
		TypeTreeNode root = new TypeTreeNode( false, null );
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
		TypeLink link1 = TypeLinkFactory.createLink();
		TypeLink link2 = TypeLinkFactory.createLink();
		
		TypeTreeNode child1 = new TypeTreeNode( false, link1 );
		TypeTreeNode child2 = new TypeTreeNode( false, link2 );
		
		TypeTreeNode root = new TypeTreeNode( false, null );
		root.insertAfter( child1, child2 );
	}
	
}
