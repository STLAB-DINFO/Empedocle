package it.unifi.ing.stlab.entities.test.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeEntity;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeLink;

import org.junit.Before;
import org.junit.Test;

public class CompositeLinkTargetTest {

	protected FakeCompositeEntity entity;
	protected FakeCompositeLink link;
	
	@Before
	public void setUp() {
		entity= new FakeCompositeEntity();
		entity.init();
		link = new FakeCompositeLink();
	}

	@Test
	public void testParents() {
		assertEquals( 0, entity.getParents().size());
	}

	@Test
	public void testAssignTarget() {
		link.assignTarget( entity );
		assertEquals( 1, entity.getParents().size());
		assertTrue( entity.getParents().contains( link ));

		assertEquals( entity, link.getTarget());
	}
	
	@Test
	public void testRemoveTarget() {
		link.assignTarget( entity );
		link.assignTarget( null );
		assertEquals( 0, entity.getParents().size());
		assertNull( link.getTarget());
	}

	@Test
	public void testChangeTarget() {
		FakeCompositeEntity other = new FakeCompositeEntity();
		other.init();
		
		link.assignTarget( entity );
		link.assignTarget( other );

		assertEquals( 0, entity.getParents().size());

		assertEquals( 1, other.getParents().size());
		assertTrue( other.getParents().contains( link ));

		assertEquals( other, link.getTarget() );
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddParent() {
		entity.listParents().add( link );
	}

}

