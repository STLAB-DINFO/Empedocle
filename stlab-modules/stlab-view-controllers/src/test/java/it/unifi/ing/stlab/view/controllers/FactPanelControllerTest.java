package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class FactPanelControllerTest {

	protected FactPanelController controller;
	protected FactPanel factPanel;
	
	protected Fact fact;
	protected Type type;
	
	@Before
	public void setUp() throws Exception {
		controller = new FactPanelController();
		
		factPanel = ViewerFactory.createFactPanel();
		
		type = TypeFactory.createTextualType();
		fact = FactFactory.createTextual();
		fact.assignType( type );
		
	}
	
	@Test
	public void testGetAssociatedViewer() {
		Viewer viewer1 = new OutputValue( UUID.randomUUID().toString() );
		viewer1.setType( TypeFactory.createTextualType() );
		Viewer viewer2 = new OutputValue( UUID.randomUUID().toString() );
		viewer2.setType( type );
		ViewerLink vl1 = ViewerLinkFactory.createSubViewer();
		vl1.assignSource( factPanel );
		vl1.assignTarget( viewer1 );
		
		ViewerLink vl2 = ViewerLinkFactory.createSubViewer();
		vl2.assignSource( factPanel );
		vl2.assignTarget( viewer2 );
		
		Viewer result = controller.getAssociatedViewer( factPanel, fact );
		
		assertNotNull( result );
		assertEquals( result, viewer2 );
		
	}
	
}
