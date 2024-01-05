package it.unifi.ing.stlab.reflection.lite.converter.sax;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.PhenomenonFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.UnitFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.DatatypeConverter;
import java.util.Stack;

public class MyHandler extends DefaultHandler {
	
	private enum elements {textual, qualitative, quantitative, temporal, composite}

	private final Stack<CompositeFact> compositeStack;
	private Fact result;
	
	private final FactConverterDao dao;
							
	public MyHandler(FactConverterDao dao) {
		this.compositeStack = new Stack<CompositeFact>();
		this.dao = dao;
	}

	public void startElement(String namespaceURI, String localName,
			String qualifiedName, Attributes atts) throws SAXException {
		
		String uuid = atts.getValue("type");
		Type t = findType( uuid );
		
		switch ( elements.valueOf( qualifiedName ) ) {
		case textual:
			result = FactFactory.createTextual(); 
			break;
			
		case quantitative:
			result = FactFactory.createQuantitative();
			((QuantitativeFact) result).setQuantity( new Quantity() );
			((QuantitativeFact) result).getQuantity().setUnit( findUnit( t, atts.getValue("um") ));			
			break;
			
		case qualitative:
			result = FactFactory.createQualitative();
			break;
			
		case temporal:
			result = FactFactory.createTemporal();
			break;
			
		case composite:
			result = FactFactory.createComposite();
			break;
		}
		
		result.assignType( t );
		
		if( !compositeStack.isEmpty() ) {
			CompositeFact parent = compositeStack.lastElement();
			
			new FactLinkFactory();
			FactLink fl = FactLinkFactory.insertLink( parent, result );
			fl.setType( findTypeLink( parent.getType(), uuid ));
		}	
		
		if( result instanceof CompositeFact )
			compositeStack.push( (CompositeFact) result );
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String value = new String(ch, start, length);

		if ( result instanceof TextualFact )
			((TextualFact) result).setText( value );
		
		else if ( result instanceof QuantitativeFact )
			((QuantitativeFact) result).getQuantity().setValue( new Double( value ) );

		else if ( result instanceof QualitativeFact )
			((QualitativeFact) result).setPhenomenon( findPhenomenon( result.getType(), value ) );
		
		else if ( result instanceof TemporalFact )
			((TemporalFact) result).setDate( DatatypeConverter.parseDate(value).getTime() );
	}			

	public void endElement( String namespaceURI, String localName,
			String qualifiedName ) throws SAXException {
		
		if( elements.valueOf( qualifiedName ) == elements.composite )
			result = compositeStack.pop();
	}
	
    private String getParseExceptionInfo(SAXParseException spe) {
        String systemId = spe.getSystemId();

        if (systemId == null) {
            systemId = "null";
        }

        String info = "URI=" + systemId + " Line=" 
            + spe.getLineNumber() + ": " + spe.getMessage();

        return info;
    }
	
    public void warning( SAXParseException spe ) throws SAXException {
        System.out.println("Warning: " + getParseExceptionInfo( spe ));
    }
        
    public void error( SAXParseException spe ) throws SAXException {
        String message = "Error: " + getParseExceptionInfo( spe );
        throw new SAXException( message );
    }

    public void fatalError(SAXParseException spe) throws SAXException {
        String message = "Fatal Error: " + getParseExceptionInfo(spe);
        throw new SAXException(message);
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

	private Type findType( String uuid ) {
		Type result = null;
		
		if( compositeStack.isEmpty() ) 
			result = dao.findType(uuid);
		else
			result = findTypeLink( compositeStack.lastElement().getType(), uuid ).getTarget();
		
		return result;
	}

	public Fact getResult() {
		return result;
	}	
}
