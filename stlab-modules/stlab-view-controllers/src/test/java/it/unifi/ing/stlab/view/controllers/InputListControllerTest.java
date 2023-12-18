package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.List;
import java.util.UUID;

import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class InputListControllerTest {

	protected InputListController controller;
	protected CompositeFact cmp;
	protected TypeSelector sel;
	protected FactLinkFactory factLinkFactory;

	protected FacesContext facesContext;

	@Before
	public void setUp() {
		controller = new InputListController();

		cmp = FactFactory.createComposite();
		Type source = TypeFactory.createCompositeType();
		Type target = TypeFactory.createTextualType();
		TypeLink tl = TypeLinkFactory.createLink();
		tl.setMax(3);
		tl.setMin(1);
		tl.assignSource(source);
		tl.assignTarget(target);
		cmp.assignType(source);
		
		sel = new TypeSelector(UUID.randomUUID().toString());
		sel.setTypeLink(tl);
		factLinkFactory = new FactLinkFactory();
		
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}	

	@Test
	public void testGetLinks(){
		Fact fact1 = FactFactory.createTextual();
		Fact fact2 = FactFactory.createTextual();
		Fact fact3 = FactFactory.createTextual();
		
		FactLink link1 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact1);
		link1.setType(sel.getTypeLink());
		FactLink link2 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact2);
		link2.setType(TypeLinkFactory.createLink());
		FactLink link3 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact3);
		link3.setType(sel.getTypeLink());
		
		List<FactLink> result = controller.getLinks(cmp, sel);
		
		assertEquals(2, result.size());
		
	}
	
	
	@Test
	public void testGetLinksOrdered(){
		Fact fact1 = FactFactory.createTextual();
		Fact fact2 = FactFactory.createTextual();
		Fact fact3 = FactFactory.createTextual();
		
		FactLink link1 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact1);
		link1.setType(sel.getTypeLink());
		((FactLinkImpl)link1).setPriority(1l);
		FactLink link2 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact2);
		link2.setType(TypeLinkFactory.createLink());
		FactLink link3 = factLinkFactory.insertLink((FactImpl)cmp, (FactImpl)fact3);
		link3.setType(sel.getTypeLink());
		((FactLinkImpl)link3).setPriority(0l);
		
		List<FactLink> result = controller.getLinks(cmp, sel);
		
		assertEquals(2, result.size());
		assertEquals(link3, result.get(0));
		assertEquals(link1, result.get(1));
		
	}

//	@Test
//	public void testAddFact(){
//		assertEquals(0, cmp.listChildren().size());
//		controller.addFact(cmp, sel);
//		assertEquals(1, cmp.listChildren().size());
//		controller.addFact(cmp, sel);
//		controller.addFact(cmp, sel);
//		assertEquals(3, cmp.listChildren().size());
//	}
//	
//
//	@Test
//	public void testAddFactNull1(){
//		controller.addFact(null, sel);
//
//	}
//	
//	@Test
//	public void testAddFactNull2(){
//		controller.addFact(cmp, null);
//
//	}
//	
//	@Test(expected=RuntimeException.class)
//	public void testAddFactFail1(){
//		controller.addFact(cmp, sel);
//		controller.addFact(cmp, sel);
//		controller.addFact(cmp, sel);
//		controller.addFact(cmp, sel);
//
//	}
//	
//	@Test(expected=RuntimeException.class)
//	public void testAddFactFail2(){
//		sel.setTypeLink(TypeLinkFactory.createLink());
//		controller.addFact(cmp, sel);
//	}
//	
//	@Test
//	public void testRemoveInsert(){
//		FactLink fl1 = factLinkFactory.insertLink(cmp, FactFactory.createTextual());
//		FactLink fl2 = factLinkFactory.insertLink(cmp, FactFactory.createTextual());
//
//		controller.removeFact(fl1);
//		assertEquals(1, cmp.listChildren().size());
//		assertTrue(cmp.listActiveLinks().contains(fl2));
//		
//		controller.removeFact(fl2);
//		assertEquals(0, cmp.listChildren().size());
//	}
//	
//	@Test
//	public void testRemoveUpdate(){
//		Fact f =  FactFactory.createTextual();
//		FactLink fl1 = factLinkFactory.insertLink(cmp, FactFactory.createTextual());
//		FactLink fl2 = factLinkFactory.insertLink(cmp, f);
//		FactLink ul = factLinkFactory.updateLink(cmp, f, fl2);
//
//		controller.removeFact(ul);
//		assertEquals(3, cmp.listChildren().size());
//		assertEquals(1, cmp.listActiveLinks().size());
//		assertTrue(cmp.listActiveLinks().contains(fl1));
//	}
//	
//	@Test
//	public void testRemoveNull(){
//		controller.removeFact(null);
//
//	}
	
	@Test
	public void testCanAddFact(){
		assertTrue(controller.canAddFact(cmp, sel));
		
	}
	
	@Test
	public void testCanAddFactFail(){
		Type source = TypeFactory.createCompositeType();
		Type target = TypeFactory.createTextualType();
		TypeLink tl = TypeLinkFactory.createLink();
		tl.setMax(1);
		tl.setMin(1);
		tl.assignSource(source);
		tl.assignTarget(target);
		cmp.assignType(source);
		
		boolean result = controller.canAddFact(cmp, sel);
		
		assertFalse(result);
		
	}
	
}
