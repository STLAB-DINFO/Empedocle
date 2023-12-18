package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;

import org.junit.Before;
import org.junit.Test;

public class TabbedPanelControllerTest {

	protected TabbedPanelController controller;
	protected FactLinkFactory linkFactory;

	protected TabbedPanel tabbedPanel;
	protected Tab t1;
	protected Tab t2;
	
	@Before
	public void setUp(){
		controller = new TabbedPanelController();
		linkFactory = new FactLinkFactory();

		tabbedPanel = ViewerFactory.createTabbedPanel();
		t1 = (Tab)ViewerLinkFactory.createTab();
		t1.assignSource( tabbedPanel );
		t1.setLabel( "t1" );
		
		t2 = (Tab)ViewerLinkFactory.createTab();
		t2.assignSource( tabbedPanel );
		t2.setLabel( "t2" );
	}
	
	@Test
	public void test() {
		assertEquals( "t1", controller.getRenderedTab( tabbedPanel ) );
		
		controller.activateTab( tabbedPanel, t2.getLabel() );
		
		assertEquals( "t2", controller.getRenderedTab( tabbedPanel ) );
	}
	
}
