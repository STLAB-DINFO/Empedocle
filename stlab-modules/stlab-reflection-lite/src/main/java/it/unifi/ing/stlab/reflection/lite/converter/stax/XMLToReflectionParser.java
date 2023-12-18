package it.unifi.ing.stlab.reflection.lite.converter.stax;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.PhenomenonFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.UnitFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.io.StringReader;
import java.util.Date;
import java.util.Stack;

import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLToReflectionParser  {
	
	private XMLEventReader xmlEventReader;
	
	private enum elements {textual, qualitative, quantitative, temporal, composite};
	
	private FactConverterDao dao;
	
	private Stack<CompositeFact> compositeStack;
	private Fact result;
	
	public XMLToReflectionParser( FactConverterDao dao ) {
		this.dao = dao;
		this.compositeStack = new Stack<CompositeFact>();
	}
	
	public Fact parse(String xml) throws XMLStreamException {
		StringReader reader = new StringReader(xml);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		xmlEventReader = factory.createXMLEventReader(reader);
		
		while (xmlEventReader.hasNext()) {
			XMLEvent xmlEvent = xmlEventReader.nextEvent();

			if (xmlEvent.getEventType() == XMLStreamConstants.START_ELEMENT) {
				StartElement startElement = xmlEvent.asStartElement();
				String tagName = startElement.getName().getLocalPart();
				
				String uuid = startElement.getAttributeByName(new QName(
						"type")).getValue();
				
				switch ( elements.valueOf( tagName ) ) {
				case textual:
					xmlEvent = xmlEventReader.nextEvent();
					if( xmlEvent.getEventType() == XMLStreamConstants.CHARACTERS )
						result = xmlToTextualFact(uuid, xmlEvent.asCharacters().getData());
					else
						result = xmlToTextualFact(uuid, null);
					break;
					
				case temporal:
					xmlEvent = xmlEventReader.nextEvent();

					if( xmlEvent.getEventType() == XMLStreamConstants.CHARACTERS )
						result = xmlToTemporalFact(uuid, xmlEvent.asCharacters().getData());
					else
						result = xmlToTemporalFact(uuid, null);
					break;
					
				case quantitative:
					Attribute um = startElement.getAttributeByName(new QName(
							"um"));

					xmlEvent = xmlEventReader.nextEvent();

					if( xmlEvent.getEventType() == XMLStreamConstants.CHARACTERS )
						result = xmlToQuantitativeFact(uuid, um.getValue(), xmlEvent.asCharacters().getData());
					else
						result = xmlToQuantitativeFact(uuid, um.getValue(), null);
					break;
					
				case qualitative:
					xmlEvent = xmlEventReader.nextEvent();

					if( xmlEvent.getEventType() == XMLStreamConstants.CHARACTERS )
						result = xmlToQualitativeFact(uuid, xmlEvent.asCharacters().getData());
					else
						result = xmlToQualitativeFact(uuid, null);
					break;
					
				case composite:
					result = xmlToCompositeFact(uuid);
					break;
				}
				
				if( !compositeStack.isEmpty() ) {
					CompositeFact parent = compositeStack.lastElement();
					
					new FactLinkFactory();
					FactLink fl = FactLinkFactory.insertLink( parent, result );
					fl.setType( findTypeLink( parent.getType(), uuid ));
				}	
				
				if( result instanceof CompositeFact )
					compositeStack.push( (CompositeFact) result );
			}

			if (xmlEvent.getEventType() == XMLStreamConstants.END_ELEMENT) {
				EndElement endElement = xmlEvent.asEndElement();
				String tagName = endElement.getName().getLocalPart();
				
				if ("composite".equals( tagName )) {
					result = compositeStack.pop();
				}
			}
		}
		return result;
	}	

	private CompositeFact xmlToCompositeFact( String uuid ) {
		Type ct = findType( uuid );
		
		CompositeFact result = FactFactory.createComposite();
		result.assignType( ct );
		
		return result;
	}
	
	private QualitativeFact xmlToQualitativeFact(String uuid, String value) {
		Type qt = findType( uuid );
		
		QualitativeFact result = FactFactory.createQualitative();
		result.setPhenomenon( findPhenomenon( qt, value ));
		result.assignType( qt );
		
		return result;
	}

	private TextualFact xmlToTextualFact( String uuid, String value ) {
		Type tt = findType( uuid );
		
		TextualFact result = FactFactory.createTextual(); 
		result.setText( value );
		result.assignType( tt );
		
		return result;
	}
	
	private TemporalFact xmlToTemporalFact( String uuid, String value ) {
		Type dt = findType( uuid );
		
		TemporalFact result = FactFactory.createTemporal(); 
		result.setDate( getDate(value) );
		result.assignType( dt );
		
		return result;
	}

	private QuantitativeFact xmlToQuantitativeFact( String type_uuid, String um_uuid, String value ) {
		Type qt = findType( type_uuid );

		QuantitativeFact result = FactFactory.createQuantitative();
		result.setQuantity( new Quantity() );
		result.getQuantity().setUnit( findUnit( qt, um_uuid ));
		result.getQuantity().setValue( getQtValue(value) );
		result.assignType( qt );
		
		return result;
	}

	private Double getQtValue(String value) {
		if( value == null || value.isEmpty() )
			return null;
		
		return new Double( value );
	}
	
	private Date getDate(String value) {
		if( value == null || value.isEmpty() )
			return null;
		
		return DatatypeConverter.parseDate(value).getTime();
	}
	
	private Phenomenon findPhenomenon( Type type, String uuid ) {
		if( uuid == null || uuid.isEmpty() ) return null;
		
		if ( ClassHelper.instanceOf( type, EnumeratedType.class )) {
			PhenomenonFinderVisitor visitor = new PhenomenonFinderVisitor( uuid );
			type.accept( visitor );
			return visitor.getResult();
		} else {
			return (uuid == null || uuid.isEmpty()) ? null : dao.findPhenomenon( uuid );
		}
	}
	
	private Unit findUnit( Type type, String uuid ) {
		UnitFinderVisitor visitor = new UnitFinderVisitor( uuid );
		type.accept( visitor );
		return visitor.getResult();
	}
	
	private Type findType( String uuid ) {
		Type result = null;
		
		if( compositeStack.isEmpty() ) 
			result = dao.findType(uuid);
		else
			result = findTypeLink( compositeStack.lastElement().getType(), uuid ).getTarget();
		
		return result;
	}
	
	private TypeLink findTypeLink( Type type, String uuid ) {
		if ( !ClassHelper.instanceOf( type, CompositeType.class ))
			throw new IllegalArgumentException( "Type '" + type.getUuid() + "' is not composite" );
		
		CompositeType compositeType = ClassHelper.cast( type, CompositeType.class );
		for ( TypeLink link : compositeType.listChildren() ) {
			if ( link.getUuid().equals( uuid )) {
				return link;
			}
		}
		
		throw new RuntimeException( "Type link '" + uuid + "' not found" );
	}
}