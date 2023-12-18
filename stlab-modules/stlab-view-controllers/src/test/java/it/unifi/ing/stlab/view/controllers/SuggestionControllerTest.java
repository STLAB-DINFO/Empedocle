package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.FieldUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class SuggestionControllerTest {

	protected SuggestionController controller;
	protected EnumeratedType enumerated;
	protected Phenomenon f1;
	protected Phenomenon f2;
	protected Phenomenon f3;
	protected QualitativeFact qf;
	protected FacesContext facesContext;

	@Before
	public void setUp() throws Exception{
		controller = new SuggestionController();
		qf = FactFactory.createQualitative();
		
		enumerated = TypeFactory.createEnumeratedType();
		f1 = PhenomenonFactory.createPhenomenon();
		f1.setName("Fenomeno1");
		f2 = PhenomenonFactory.createPhenomenon();
		f2.setName("Fenomeno2");
		f3 = PhenomenonFactory.createPhenomenon();
		f3.setName("Altro Fenomeno");
		enumerated.addPhenomenon(f1);
		qf.assignType(enumerated);
		
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}
	
	@Test
	public void testIsSupported() throws Exception {
		Fact qlt = FactFactory.createQualitative();
		
		boolean result = controller.isSupported(qlt);
		
		assertNotNull( result );
		assertTrue( result );
	}	
	 
	@Test
	public void testIsSupportedFail() throws Exception {
		TextualFact txt = FactFactory.createTextual();
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
