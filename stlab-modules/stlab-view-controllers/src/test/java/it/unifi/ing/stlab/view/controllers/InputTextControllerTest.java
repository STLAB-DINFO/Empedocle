package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.test.FieldUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;


public class InputTextControllerTest {
	
	protected InputTextController controller;
	protected FacesContext facesContext;

	@Before
	public void setUp() {
		controller = new InputTextController();
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}	
	
	@Test
	public void testIsOsservazioneTestuale() throws Exception {
		Fact txt = FactFactory.createTextual();
		
		boolean result = controller.isOsservazioneTestuale(txt);
		
		assertNotNull( result );
		assertTrue( result );
	}
	
	@Test
	public void testIsOsservazioneQuantitativa() throws Exception {
		Fact qnt = FactFactory.createQuantitative();
		
		boolean result = controller.isOsservazioneQuantitativa(qnt);
		
		assertNotNull( result );
		assertTrue( result );
	}
	
	@Test
	public void testIsSupported() throws Exception {
		Fact txt = FactFactory.createTextual();
		
		boolean result = controller.isSupported(txt);
		
		assertNotNull( result );
		assertTrue( result );
	}
	
	@Test
	public void testIsSupportedFail() throws Exception {
		Fact qlt = FactFactory.createQualitative();
		qlt.assignType(TypeFactory.createEnumeratedType());
		
		boolean result = controller.isSupported(qlt);
		
		assertFalse( result );
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));

	}
	
	@Test
	public void testIsSupportedNull() throws Exception {
		boolean result = controller.isSupported(null);
		
		assertFalse( result );
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));

	}
	
}