package it.unifi.ing.stlab.reflection.lite.converter.stax;

import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.util.GregorianCalendar;

public class ReflectionToXMLVisitor implements FactVisitor {

	private boolean nested;
	private TypeLink typeLink;
	
	final String DEFAULT_NAMESPACE = "http://www.stlab.dsi.unifi.it/reflection/facts";
	
	private XMLStreamWriter xmlsw;
	private final StringWriter writer;
	
	public ReflectionToXMLVisitor() {
	    XMLOutputFactory xof = XMLOutputFactory.newInstance();
		writer = new StringWriter();
		
		try {
			xmlsw = xof.createXMLStreamWriter(writer);
			xmlsw.writeStartDocument( "UTF-8\" standalone=\"yes", "1.0");
			xmlsw.setDefaultNamespace( DEFAULT_NAMESPACE );
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public String getResult() {
		return writer.toString();
	}
		
	private boolean isRootNode( Fact fact ) {
		return fact.listParents().size() == 0;
	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		try {
			xmlsw.writeStartElement("textual");
			xmlsw.writeAttribute("type", uuid( fact.getType()) );

			if ( isRootNode( fact )) {
				xmlsw.writeDefaultNamespace( DEFAULT_NAMESPACE );
			} 

			xmlsw.writeCharacters( fact.getText() );
			xmlsw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		try {
			xmlsw.writeStartElement("quantitative");
			xmlsw.writeAttribute("type", uuid( fact.getType()) );
			xmlsw.writeAttribute("um", fact.getQuantity().getUnit().getUuid());
			
			if ( isRootNode( fact )) {
				xmlsw.writeDefaultNamespace( DEFAULT_NAMESPACE );
			} 
			
			if( fact.getQuantity().getValue() != null )
				xmlsw.writeCharacters( fact.getQuantity().getValue().toString() );
			
			xmlsw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		try {
			xmlsw.writeStartElement("qualitative");
			xmlsw.writeAttribute("type", uuid( fact.getType()) );
			
			if ( isRootNode( fact )) {
				xmlsw.writeDefaultNamespace( DEFAULT_NAMESPACE );
			} 
			
			if( fact.getPhenomenon() != null )
				xmlsw.writeCharacters( fact.getPhenomenon().getUuid() );
			
			xmlsw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		try {
			xmlsw.writeStartElement("temporal");
			xmlsw.writeAttribute("type", uuid( fact.getType()) );
			
			if ( isRootNode( fact )) {
				xmlsw.writeDefaultNamespace( DEFAULT_NAMESPACE );
			} 
			
			if( fact.getDate() != null ) {
				GregorianCalendar c = new GregorianCalendar();
				c.setTime( fact.getDate() );
				xmlsw.writeCharacters( DatatypeFactory.newInstance().newXMLGregorianCalendar(c).toXMLFormat() );
			}
			
			xmlsw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void visitComposite(CompositeFact fact) {
		boolean oldNested = nested;
		TypeLink oldTypeLink = typeLink;
		
		try {
			xmlsw.writeStartElement("composite");
			xmlsw.writeAttribute("type", uuid( fact.getType()) );
			
			if ( isRootNode( fact )) {
				xmlsw.writeDefaultNamespace( DEFAULT_NAMESPACE );
			} 
			
			for ( FactLink link : fact.listChildrenOrdered() ) {
				nested = true;
				typeLink = link.getType();

				link.getTarget().accept( this );
			}
			
			nested = oldNested;
			typeLink = oldTypeLink;

			xmlsw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private String uuid( Type type ) {
		if ( nested ) {
			return typeLink.getUuid();
		} else {
			return type.getUuid();
		}
	}
}
