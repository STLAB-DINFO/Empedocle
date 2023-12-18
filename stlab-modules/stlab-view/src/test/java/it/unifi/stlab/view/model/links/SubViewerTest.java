package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.model.links.SubViewer;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class SubViewerTest {

	protected SubViewer sottoVista;
	
	@Before
	public void setUp() {
		sottoVista = new SubViewer( UUID.randomUUID().toString() );
	}
	
	@Test
	public void testId() {
		assertNull( sottoVista.getId() );
	}

	@Test
	public void testUuid() {
		assertNotNull( sottoVista.getUuid() );
	}
	
	@Test
	public void testHashCodeEquals() {
		String u1 = UUID.randomUUID().toString();
		String u2 = UUID.randomUUID().toString();
		
		SubViewer v1 = new SubViewer( u1 );
		SubViewer v2 = new SubViewer( u1 );
		SubViewer v3 = new SubViewer( u2 );
		
		assertTrue( v1.equals( v2 ));
		assertTrue( v2.equals( v1 ));
		assertFalse( v1.equals( v3 ));
		
		assertTrue( v1.hashCode() == v2.hashCode() );
		assertFalse( v1.hashCode() == v3.hashCode() );
	}
}
