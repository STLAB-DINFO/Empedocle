package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class GridControllerTest {

	protected GridController controller;
	protected FactLinkFactory linkFactory;
	protected Grid container;

	@Before
	public void setUp(){
		controller = new GridController();
		linkFactory = new FactLinkFactory();
		container = new Grid( UUID.randomUUID().toString() );
	}
	
	@Test
	public void testFindBySelectorNull() {
		Fact oss = FactFactory.createTextual();
		
		Fact result = controller.findBySelector(oss, null);
		
		assertNotNull( result );
		assertEquals( result, oss );
	}
	
	@Test
	public void testShow1(){
		container.setCollapse(false);
		boolean result = controller.show( container, FactFactory.createTextual() );
		
		assertTrue( result );
	}
	
	@Test
	public void testShow2(){
		container.setCollapse(true);
		
		TextualFact fact = FactFactory.createTextual();
		fact.setText( "ciao" );
		boolean result = controller.show( container, fact );
		
		assertTrue( result );
	}
	
	@Test
	public void testShow3(){
		container.setCollapse(true);
		boolean result = controller.show( container, FactFactory.createTextual() );
		
		assertFalse( result );
	}
	
	@Test
	public void testShow4(){
		container.setCollapse(true);
		boolean result = controller.show( container, null );
		
		assertFalse( result );
	}
	
	@Test
	public void isVistaList1(){
		Viewer v = new InputList(UUID.randomUUID().toString());
		
		assertTrue(controller.isVistaCompositaItem(v));
	}
	
	@Test
	public void isVistaList2(){
		Viewer v = new OutputList(UUID.randomUUID().toString());
		
		assertTrue(controller.isVistaCompositaItem(v));
	}
	
	@Test
	public void isVistaListFail(){
		Viewer v = new Grid(UUID.randomUUID().toString());
		
		assertFalse(controller.isVistaCompositaItem(v));
	}
}
