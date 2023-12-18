package it.unifi.ing.stlab.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLUtilsTest {
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void dom2StringTest() throws ParserConfigurationException, TransformerException {
		// build dom
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element table = doc.createElement( "table" );
		doc.appendChild( table );
		Element tr = doc.createElement( "tr" );
		table.appendChild( tr);
		Element td = doc.createElement( "td" );
		tr.appendChild( td);
		td.setTextContent( "prova" );
		
		String actual = XMLUtils.dom2String( doc, false );
		String expected ="<table><tr><td>prova</td></tr></table>";
		
		assertEquals( expected, actual );
	}

	@Test
	public void indentTest() throws IOException, TransformerException, URISyntaxException {
		String actual = XMLUtils.indent( 
				FileUtils.readFile( new File( Thread.currentThread().getContextClassLoader()
						.getResource( "books-no-indent.xml" ).toURI() )));
		
		String expected = FileUtils.readFile( new File( Thread.currentThread().getContextClassLoader()
				.getResource( "books-indent.xml" ).toURI() ));
		
		assertEquals( expected, actual );

	}
}
