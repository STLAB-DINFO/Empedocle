package it.unifi.ing.stlab.reflection.lite.converter.sax;

import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

public class XMLToReflectionParser {
	
	private final FactConverterDao dao;
	
	public XMLToReflectionParser( FactConverterDao dao ) {
		this.dao = dao;
	}
	
	public Fact parse(String xml) throws ParserConfigurationException,
			SAXException, IOException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
				
		MyHandler handler = new MyHandler( dao );
		
		saxParser.parse(new InputSource( new StringReader( xml )), handler);
		
		return handler.getResult();
	}	
}
