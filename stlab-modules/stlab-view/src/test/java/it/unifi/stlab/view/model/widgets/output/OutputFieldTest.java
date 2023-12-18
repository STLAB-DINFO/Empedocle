package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class OutputFieldTest {

	protected OutputField outputField;
	
	@Before
	public void setUp(){
		outputField = new OutputField(UUID.randomUUID().toString());
	}
	
	@Test
	public void testSetRootNew(){
		outputField.setRoot("Visita");
		
		assertEquals("Visita", outputField.getPath());
	}
	
	@Test
	public void testSetRootReplace(){
		outputField.setPath("Visita.Nome");
		outputField.setRoot("Paziente");
		
		assertEquals("Paziente.Nome", outputField.getPath());
	}
	
	@Test
	public void testGetRoot(){
		outputField.setPath("Visita.Nome");
		String root = outputField.getRoot();
		
		assertEquals("Visita", root);
	}
	
	@Test
	public void testAddFieldPath(){
		outputField.setRoot("Paziente");
		outputField.addFieldPath("Indirizzo");
		
		assertEquals("Paziente.Indirizzo", outputField.getPath());
		
		outputField.addFieldPath("Via");
		
		assertEquals("Paziente.Indirizzo.Via", outputField.getPath());
	}
	
}
