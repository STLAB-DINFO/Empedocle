package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ConditionalPanelTest {

	protected ConditionalPanel panel;
	
	@Before
	public void setUp(){
		panel = new ConditionalPanel(UUID.randomUUID().toString());
		
	}
	
	@Test
	public void testGetTarget(){
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		Viewer v = new Label(UUID.randomUUID().toString());
		sv.assignTarget(v);
		
		sv.assignSource(panel);

		assertEquals(v, panel.getTarget());
	}
	
	@Test
	public void testGetSelector(){
		TypeSelector s = new TypeSelector(UUID.randomUUID().toString());
		SubViewer sv = new SubViewer(UUID.randomUUID().toString());
		Viewer v = new Label(UUID.randomUUID().toString());
		sv.assignTarget(v);
		sv.setSelector(s);
		
		sv.assignSource(panel);

		assertEquals(s, panel.getSelector());
	}
	
}
