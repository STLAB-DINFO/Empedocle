package it.unifi.stlab.view.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.stlab.view.model.widgets.FakeViewer;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ViewerTest {
	
	protected Viewer vista;
	
	@Before
	public void setUp() {
		vista = new FakeViewer( UUID.randomUUID().toString() );
	}
	
	@Test
	public void testId() {
		assertNull( vista.getId() );
	}
	
	@Test
	public void testUuid() {
		assertNotNull( vista.getUuid() );
	}
	
	@Test
	public void testHashCodeEquals() {
		String u1 = UUID.randomUUID().toString();
		String u2 = UUID.randomUUID().toString();
		
		Viewer v1 = new FakeViewer( u1 );
		Viewer v2 = new FakeViewer( u1 );
		Viewer v3 = new FakeViewer( u2 );
		
		assertTrue( v1.equals( v2 ));
		assertTrue( v2.equals( v1 ));
		assertFalse( v1.equals( v3 ));
		
		assertTrue( v1.hashCode() == v2.hashCode() );
		assertFalse( v1.hashCode() == v3.hashCode() );
	}

	@Test
	public void testGetSottoviste() {
		assertEquals( 0, vista.listChildren().size() );
	}
	
	@Test
	public void testAddSottoVista() {
		SubViewer sv = new SubViewer( UUID.randomUUID().toString() );
		
		sv.assignSource(vista);
		assertEquals( 1, vista.listChildren().size() );
		assertTrue( vista.listChildren().contains( sv ));
	}

//	@Test
//	public void testAddSottoVistaNoNull() {
//		vista.addSottoVista( null );
//		assertEquals( 0, vista.getSottoViste().size() );
//	}
	
	@Test
	public void testAddSottoVistaNoDuplicate() {
		SubViewer sv = new SubViewer( UUID.randomUUID().toString() );
		
		sv.assignSource(vista);
		sv.assignSource(vista);
		assertEquals( 1, vista.listChildren().size() );
	}

	@Test
	public void testGetByPriority(){
		SubViewer sv = new SubViewer( UUID.randomUUID().toString() );
		SubViewer sv2 = new SubViewer( UUID.randomUUID().toString() );
		SubViewer sv3 = new SubViewer( UUID.randomUUID().toString() );
	
		sv.assignSource(vista);
		sv2.assignSource(vista);
		sv3.assignSource(vista);
		
		assertEquals(sv3, vista.getByPriority(2l));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetByPriorityException(){
		vista.getByPriority(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetByPriorityException2(){
		vista.getByPriority(-3l);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetByPriorityNotFound(){
		SubViewer sv = new SubViewer( UUID.randomUUID().toString() );
		SubViewer sv2 = new SubViewer( UUID.randomUUID().toString() );
		SubViewer sv3 = new SubViewer( UUID.randomUUID().toString() );

		sv.assignSource(vista);
		sv2.assignSource(vista);
		sv3.assignSource(vista);
		
		vista.getByPriority(5l);
	}
	
}
