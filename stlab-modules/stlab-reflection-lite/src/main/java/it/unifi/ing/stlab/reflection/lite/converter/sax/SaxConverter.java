package it.unifi.ing.stlab.reflection.lite.converter.sax;

import it.unifi.ing.stlab.reflection.lite.converter.FactConverter;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SaxConverter implements FactConverter {
	
	private final FactConverterDao dao;
	
	public SaxConverter(FactConverterDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String serialize( Fact fact ) {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public Fact deserialize( String xml ) {
		XMLToReflectionParser xmlParser = new XMLToReflectionParser( dao );
		try {
			return xmlParser.parse( xml );
		} catch( ParserConfigurationException pe ) {
			throw new RuntimeException( pe );
			
		} catch( SAXException se ) {
			throw new RuntimeException( se );
			
		} catch( IOException ie ) {
			throw new RuntimeException( ie );
			
		}
	}
	
}
