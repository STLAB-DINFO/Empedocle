package it.unifi.ing.stlab.entities.test.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeEntity;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeLink;

import org.junit.Before;
import org.junit.Test;

public class CompositeLinkSourceTest {

	protected FakeCompositeEntity entity;
	protected FakeCompositeLink link;
	
	@Before
	public void setUp() {
		entity= new FakeCompositeEntity();
		entity.init();
		
		link = new FakeCompositeLink();
	}

	@Test
	public void testChildren() {
		assertEquals( 0, entity.getChildren().size());
	}

	@Test
	public void testAssignSource() {
		link.assignSource( entity );
		assertEquals( 1, entity.getChildren().size());
		assertTrue( entity.getChildren().contains( link ));

		assertEquals( entity, link.getSource() );
	}
	
	@Test
	public void testRemoveSource() {
		link.assignSource( entity );
		link.assignSource( null );
		assertEquals( 0, entity.getChildren().size());
		assertNull( link.getSource() );
	}

	@Test
	public void testChangeSource() {
		FakeCompositeEntity other = new FakeCompositeEntity();
		other.init();
		
		link.assignSource( entity );
		link.assignSource( other );

		assertEquals( 0, entity.getChildren().size());

		assertEquals( 1, other.getChildren().size());
		assertTrue( other.getChildren().contains( link ));

		assertEquals( other, link.getSource() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddChild() {
		entity.listChildren().add( link );
	}

}
