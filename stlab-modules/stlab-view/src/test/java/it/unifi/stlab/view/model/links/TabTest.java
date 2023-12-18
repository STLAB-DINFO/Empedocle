package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class TabTest {

	protected Tab tab;
	
	@Before
	public void setUp() {
		tab = new Tab( UUID.randomUUID().toString() );
	}
	
	@Test
	public void testId() {
		assertNull( tab.getId() );
	}

	@Test
	public void testUuid() {
		assertNotNull( tab.getUuid() );
	}
	
	@Test
	public void testAddSourceTabbed(){
		TabbedPanel source = new TabbedPanel(UUID.randomUUID().toString());
		tab.assignSource(source);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddSourceNotTabbed(){
		Grid source = new Grid(UUID.randomUUID().toString());
		tab.assignSource(source);
	}
	
	@Test
	public void testHashCodeEquals() {
		String u1 = UUID.randomUUID().toString();
		String u2 = UUID.randomUUID().toString();
		
		Tab v1 = new Tab( u1 );
		Tab v2 = new Tab( u1 );
		Tab v3 = new Tab( u2 );
		
		assertTrue( v1.equals( v2 ));
		assertTrue( v2.equals( v1 ));
		assertFalse( v1.equals( v3 ));
		
		assertTrue( v1.hashCode() == v2.hashCode() );
		assertFalse( v1.hashCode() == v3.hashCode() );
	}
}
