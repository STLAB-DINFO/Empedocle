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


public class ComboControllerTest {
	
	protected ComboController controller;
	protected FacesContext facesContext;

	@Before
	public void setUp() {
		controller = new ComboController();
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}	
	
	@Test
	public void testIsOsservazioneQualitativa() throws Exception {
		Fact qlt = FactFactory.createQualitative();
		
		boolean result = controller.isOsservazioneQualitativa(qlt);
		
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
		Fact qnt = FactFactory.createQuantitative();
		
		boolean result = controller.isSupported(qnt);
		
		assertNotNull( result );
		assertTrue( result );
	}
	
	@Test
	public void testIsSupportedFail() throws Exception {
		Fact txt = FactFactory.createTextual();
		txt.assignType(TypeFactory.createTextualType());
		
		boolean result = controller.isSupported(txt);
		
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