package it.unifi.ing.stlab.entities.test.composite;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeEntity;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeLink;

import org.junit.Before;
import org.junit.Test;

public class CompositeEntityAncestorsTest {

	private FakeCompositeEntity e1, e2, e3, e4, e5;
	private FakeCompositeLink l1, l2, l3, l4;
	
	@Before
	public void setUp() {
		e1 = new FakeCompositeEntity();
		e1.init();
		
		e2 = new FakeCompositeEntity();
		e2.init();
	
		e3 = new FakeCompositeEntity();
		e3.init();
	
		e4 = new FakeCompositeEntity();
		e4.init();
	
		e5 = new FakeCompositeEntity();
		e5.init();
		
		l1 = new FakeCompositeLink();
		l2 = new FakeCompositeLink();
		l3 = new FakeCompositeLink();
		l4 = new FakeCompositeLink();

		l1.assignSource( e1 );
		l1.assignTarget( e2 );

		l2.assignSource( e2 );

		l3.assignTarget( e4 );

		l4.assignSource( e4 );
		l4.assignTarget( e5 );
	}
	
	@Test
	public void testSelfAncestor() {
		assertTrue( e3.getAncestors().contains( e3 ));
	}

	@Test
	public void testEntityAddChild() {
		l3.assignSource( e3 );
		
		assertTrue( e4.getAncestors().contains( e3 ));
		assertTrue( e5.getAncestors().contains( e3 ));
	}

	@Test
	public void testEntityMoveChild() {
		l3.assignSource( e3 );
		l3.assignSource( e1 );

		assertFalse( e4.getAncestors().contains( e3 ));
		assertFalse( e5.getAncestors().contains( e3 ));
	}
	
	@Test
	public void testEntityAddParent() {
		l2.assignTarget( e3 );

		assertTrue( e3.getAncestors().contains( e1 ));
		assertTrue( e3.getAncestors().contains( e2 ));
	}
	
	@Test
	public void testEntityMoveParent() {
		l2.assignTarget( e3 );
		l2.assignTarget( e5 );

		assertFalse( e3.getAncestors().contains( e1 ));
		assertFalse( e3.getAncestors().contains( e2 ));
	}
	
	@Test
	public void testEntityRemoveChild() {
		l3.assignSource( e3 );
		l3.assignSource( null );

		assertFalse( e4.getAncestors().contains( e3 ));
		assertFalse( e5.getAncestors().contains( e3 ));
	}

	@Test
	public void testEntityRemoveParent() {
		l2.assignTarget( e3 );
		l2.assignTarget( null );

		assertFalse( e3.getAncestors().contains( e1 ));
		assertFalse( e3.getAncestors().contains( e2 ));
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddAncestor() {
		e1.listAncestors().add( e2 );
	}

	
	@Test( expected = IllegalArgumentException.class ) 
	public void testNoLoop() {
		l3.assignSource( e2 );
		l3.assignTarget( e1 );
	}
}
