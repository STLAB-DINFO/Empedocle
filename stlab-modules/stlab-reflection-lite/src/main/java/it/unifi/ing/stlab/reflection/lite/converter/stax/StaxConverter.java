package it.unifi.ing.stlab.reflection.lite.converter.stax;

import javax.xml.stream.XMLStreamException;

import it.unifi.ing.stlab.reflection.lite.converter.FactConverter;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

public class StaxConverter implements FactConverter {
	
	private FactConverterDao dao;
	
	public StaxConverter(FactConverterDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public String serialize( Fact fact ) {
		ReflectionToXMLVisitor visitor = new ReflectionToXMLVisitor();
		fact.accept( visitor );
		
		return visitor.getResult();
	}
	
	@Override
	public Fact deserialize( String xml ) {
		XMLToReflectionParser xmlParser = new XMLToReflectionParser( dao );
		
		try {
			return xmlParser.parse( xml );
		} catch (XMLStreamException e) {
			throw new RuntimeException( e );
		}
	}
	
}
