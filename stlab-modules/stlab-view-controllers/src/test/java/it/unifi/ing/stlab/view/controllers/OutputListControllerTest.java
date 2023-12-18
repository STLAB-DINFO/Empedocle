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
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class OutputListControllerTest {

	protected OutputListController controller;
	protected TypeSelector selector;
	protected FactLinkFactory linkFactory;
	protected CompositeFact cf;
	protected TypeLink tl;
	protected FactLink fl;
	
	protected FacesContext facesContext;
	
	@Before
	public void setUp(){
		controller = new OutputListController();
		selector = new TypeSelector(UUID.randomUUID().toString());
		
		cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		linkFactory = new FactLinkFactory();
		
		fl = linkFactory.insertLink((FactImpl)cf, (FactImpl)tf);
		
		tl = TypeLinkFactory.createLink();
		fl.setType(tl);
		tl.setName("prova");
		
		selector.setTypeLink(tl);
		
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}
	
	@Test
	public void testGetLinks(){
		TextualFact tf2 = FactFactory.createTextual();
		FactInsertLink fl2 = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf2);
		TypeLink tl2 = TypeLinkFactory.createLink();
		fl2.setType(tl2);
		
		List<FactLink> result = controller.getLinks(cf, selector);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(tl, result.get(0).getType());
		
	}
	
	@Test
	public void testGetLinksMore(){
		TextualFact tf2 = FactFactory.createTextual();
		FactInsertLink fl2 = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf2);
		fl2.setType(tl);
		
		List<FactLink> result = controller.getLinks(cf, selector);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(tl, result.get(0).getType());
		assertEquals(tl, result.get(1).getType());
	}
	
	@Test
	public void testGetLinksMoreOrdered(){
		((FactLinkImpl)fl).setPriority(1l);
		
		TextualFact tf2 = FactFactory.createTextual();
		FactInsertLink fl2 = (FactInsertLink)linkFactory.insertLink((FactImpl)cf, (FactImpl)tf2);
		fl2.setPriority(0l);
		fl2.setType(tl);
		
		List<FactLink> result = controller.getLinks(cf, selector);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(fl2, result.get(0));
		assertEquals(fl, result.get(1));
	}
	
	@Test
	public void testGetValueFail1(){
		controller.getLinks(cf, null);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
	}
	
	@Test
	public void testGetValueFail2(){
		selector.setTypeLink(null);
		controller.getLinks(cf, selector);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
	}
	
//	@Test
//	public void testGetValueFail3(){
//		selector.setTypeLink(TypeLinkFactory.createLink());
//		controller.getLinks(cf, selector);
//		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
//	}
	
}
