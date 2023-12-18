package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class OutputPathControllerTest {

	protected OutputPathController controller;
	protected TypeSelector selector;
	protected FactLinkFactory linkFactory;
	protected CompositeFact cf;
	protected TypeLink tl;
	
	protected FacesContext facesContext;

	@Before
	public void setUp(){
		controller = new OutputPathController();
		selector = new TypeSelector(UUID.randomUUID().toString());
		
		cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		linkFactory = new FactLinkFactory();
		
		FactInsertLink fl = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf);
		
		tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		tl.setName("prova");
		
		selector.setTypeLink(tl);
		
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}
	
	@Test
	public void testGetValue(){
		TextualFact tf2 = FactFactory.createTextual();
		FactInsertLink fl2 = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf2);
		TypeLink tl2 = TypeLinkFactory.createLink();
		fl2.setType(tl2);
		
		String value = controller.getValue(cf, selector);
		
		assertNotNull(value);
		assertEquals("prova", value);
		
	}
	
	@Test
	public void testGetValueMore(){
		TextualFact tf2 = FactFactory.createTextual();
		FactInsertLink fl2 = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf2);
		fl2.setType(tl);
		
		String value = controller.getValue(cf, selector);
		
		assertNotNull(value);
		assertEquals("prova", value);
		
	}
	
	@Test
	public void testGetValueFail1(){
		controller.getValue(cf, null);
		
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
		
	}
	
	@Test
	public void testGetValueFail2(){
		selector.setTypeLink(null);
		controller.getValue(cf, selector);
		
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
	}
	
}
