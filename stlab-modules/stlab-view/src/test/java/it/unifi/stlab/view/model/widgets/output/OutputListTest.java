package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class OutputListTest {

	protected OutputList outputList;
	
	@Before
	public void setUp(){
		outputList = new OutputList( UUID.randomUUID().toString() );
		
	}
	
	@Test
	public void testAddSottoVista(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new OutputValue(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		sv1.assignSource(outputList);
		
		assertEquals(1, outputList.listChildren().size());
		assertTrue(outputList.listChildren().contains(sv1));
	}
	
	@Test
	public void testGetVista(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new OutputValue(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		sv1.assignSource(outputList);
		
		assertNotNull(outputList.getViewer());
		assertEquals(v1, outputList.getViewer());
	}
	
	@Test (expected=RuntimeException.class)
	public void testAddSottovistaInput(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new InputText(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		sv1.assignSource(outputList);
	}
	
	@Test (expected=RuntimeException.class)
	public void testAddSottoVistaMore(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		
		sv1.assignSource(outputList);
		sv2.assignSource(outputList);
	}
	
}
