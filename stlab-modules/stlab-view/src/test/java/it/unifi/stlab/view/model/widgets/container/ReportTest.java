package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ReportTest {

	protected Report report;
	protected Label header;
	protected Label content;
	protected Label footer;
	
	@Before
	public void setUp(){
		report = new Report(UUID.randomUUID().toString());
		
		SubViewer headerContainer = new SubViewer(UUID.randomUUID().toString());
		header = new Label(UUID.randomUUID().toString());
		headerContainer.assignTarget(header);
		
		SubViewer contentContainer = new SubViewer(UUID.randomUUID().toString());
		content = new Label(UUID.randomUUID().toString());
		contentContainer.assignTarget(content);
		
		SubViewer footerContainer = new SubViewer(UUID.randomUUID().toString());
		footer = new Label(UUID.randomUUID().toString());
		footerContainer.assignTarget(footer);
		
		headerContainer.assignSource(report);
		contentContainer.assignSource(report);
		footerContainer.assignSource(report);
	}
	
	@Test
	public void testGetHeader(){
		assertEquals(header, report.getHeader());
	}
	
	@Test
	public void testGetContent(){
		assertEquals(content, report.getContent());
	}
	
	@Test
	public void testGetFooter(){
		assertEquals(footer, report.getFooter());
	}
	
}
