package it.unifi.stlab.view.model.widgets.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class InputListTest {

	protected InputList inputList;
	
	@Before
	public void setUp() {
		inputList = new InputList( UUID.randomUUID().toString() );
	}
	
	@Test
	public void testRequired() {
		assertEquals( Boolean.FALSE, inputList.getRequired() );
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testRequiredNoSetter() {
		inputList.setRequired( Boolean.TRUE );
	}
	
	
	@Test
	public void testAddSottoVista(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new InputText(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		
		sv1.assignSource(inputList);
		
		assertEquals(1, inputList.listChildren().size());
		assertTrue(inputList.listChildren().contains(sv1));
	}
	
	@Test
	public void testGetVista(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new InputText(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		
		sv1.assignSource(inputList);

		assertNotNull(inputList.getViewer());
		assertEquals(v1, inputList.getViewer());
	}
	
	@Test(expected=RuntimeException.class)
	public void testAddSottovistaOutput(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		Viewer v1 = new Label(UUID.randomUUID().toString());
		sv1.assignTarget(v1);
		
		sv1.assignSource(inputList);
	}
	
	@Test (expected=RuntimeException.class)
	public void testAddSottoVistaMore(){
		SubViewer sv1 = new SubViewer(UUID.randomUUID().toString());
		SubViewer sv2 = new SubViewer(UUID.randomUUID().toString());
		
		sv1.assignSource(inputList);
		sv2.assignSource(inputList);
	}
	
}
	
