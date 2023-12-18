package it.unifi.ing.stlab.entities.test.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeEntity;
import it.unifi.ing.stlab.entities.test.composite.fakes.FakeCompositeLink;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CompositeEntityTest {

	protected FakeCompositeEntity compositeEntity;

	@Before
	public void setUp() {
		compositeEntity = new FakeCompositeEntity();
	}
	
	@Test
	public void testWithoutInit() {
		assertEquals( 0, compositeEntity.getAncestors().size() );
		assertEquals( 0, compositeEntity.getDescendents().size() );
	}
	
	@Test
	public void testInitAncestors() {
		compositeEntity.init();
		assertEquals( 1, compositeEntity.getAncestors().size() );
		assertTrue( compositeEntity.getAncestors().contains( compositeEntity ));

	}
	
	@Test
	public void testInitDescendents() {
		compositeEntity.init();
		assertEquals( 1, compositeEntity.getDescendents().size() );
		assertTrue( compositeEntity.getDescendents().contains( compositeEntity ));
	}
	
	@Test
	public void testListChildrenOrdered() {
		compositeEntity.init();
		
		FakeCompositeLink link1 = new FakeCompositeLink();
		FakeCompositeLink link2 = new FakeCompositeLink();
		FakeCompositeLink link3 = new FakeCompositeLink();
		
		link1.setPriority(0l);
		link1.assignSource(compositeEntity);
		
		link2.setPriority(2l);
		link2.assignSource(compositeEntity);
		
		link3.setPriority(1l);
		link3.assignSource(compositeEntity);
		
		List<FakeCompositeLink> result = compositeEntity.listChildrenOrdered();
		
		assertEquals(3, result.size());
		assertEquals(link1, result.get(0));
		assertEquals(link3, result.get(1));
		assertEquals(link2, result.get(2));
		
	}
	
}
