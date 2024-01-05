package it.unifi.ing.stlab.reflection.lite.converter.jaxb;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.converter.FactConverter;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.*;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.PhenomenonFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.converter.visitor.UnitFinderVisitor;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

public class JaxbConverter implements FactConverter {

	private final FactConverterDao dao;
	private Type type;
	private TypeLink typeLink;
	private boolean nested;
	
	public JaxbConverter(FactConverterDao dao) {
		super();
		this.dao = dao;
	}

	public Fact deserialize( String xml ) {
		return objectToReflection( xmlToObject( xml ));
	}
	
	public String serialize( Fact fact ) {
		ReflectionToObjectVisitor visitor = new ReflectionToObjectVisitor();
		fact.accept( visitor );
		return objectToXml( visitor.getResult() );
	}
	
	
	private Object xmlToObject( String xml ) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated");
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return jaxbUnmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	

	private String objectToXml( Object object ) {
		try {
			JAXBContext context = JAXBContext.newInstance("it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated");		

			StringWriter result = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.marshal( object, result );

			return result.toString();
		} catch (JAXBException e) {
			throw new RuntimeException( e );
		}		
	}
	
	private Fact objectToReflection( Object object ) {
		if ( object instanceof Textual ) {
			return objectToTextualFact( (Textual)object );
		}
		if ( object instanceof Temporal ) {
			return objectToTemporalFact( (Temporal)object );
		}
		if ( object instanceof Quantitative ) {
			return objectToQuantitativeFact( (Quantitative)object );
		}
		if ( object instanceof Qualitative ) {
			return objectToQualitativeFact( (Qualitative)object );
		}
		if ( object instanceof Composite ) {
			return objectToCompositeFact( (Composite)object );
		}

		throw new IllegalArgumentException( "Unsupported type" );
	}
	
	
	private TextualFact objectToTextualFact( Textual textual ) {
		initType( textual.getType() );

		TextualFact result = FactFactory.createTextual(); 
		
		result.setText( getTextualValue( textual ) );
		result.assignType( findType( textual.getType() ));
		
		return result;
	}

	private String getTextualValue(Textual textual) {
		if( textual.getValue() == null || textual.getValue().trim().isEmpty() )
			return null;
		
		return textual.getValue();
	}

	private TemporalFact objectToTemporalFact( Temporal temporal ) {
		initType( temporal.getType() );
		TemporalFact result = FactFactory.createTemporal(); 
		
		Date date = getTemporalValue( temporal );
		if( date != null )
			result.setDate( date );
		
		result.assignType( findType( temporal.getType() ));
		
		return result;
	}

	private QuantitativeFact objectToQuantitativeFact( Quantitative quantitative ) {
		initType( quantitative.getType() );

		Type qt = findType( quantitative.getType() );

		QuantitativeFact result = FactFactory.createQuantitative();
		result.setQuantity( new Quantity() );
		result.getQuantity().setUnit( findUnit( qt, quantitative.getUm() ));
		result.getQuantity().setValue( getQuantityValue(quantitative) );
		result.assignType( qt );
		
		return result;
	}

	private QualitativeFact objectToQualitativeFact( Qualitative qualitative ) {
		initType( qualitative.getType() );

		Type qt = findType( qualitative.getType() );
		
		QualitativeFact result = FactFactory.createQualitative();
		result.setPhenomenon( findPhenomenon( qt, qualitative.getValue() ));
		result.assignType( qt );
		
		return result;
	}

	private CompositeFact objectToCompositeFact( Composite composite ) {
		initType( composite.getType() );
		Type ct = findType( composite.getType() );

		CompositeFact result = FactFactory.createComposite();
		result.assignType( ct );
		
		boolean oldNested = nested;
		nested = true;
		Type oldType = type;
		TypeLink oldTypeLink = typeLink;
		
		type = ct;
		typeLink = null;
		
		long priority = 0;
		for ( Object child : composite.getChildren()) {
			
			Fact f = objectToReflection( child );

			new FactLinkFactory();
			FactLink fl = FactLinkFactory.insertLink( result, f );
			fl.setPriority( priority );
			fl.setType( typeLink );
			
			priority++;
		}
		
		nested = oldNested;
		type = oldType;
		typeLink = oldTypeLink;
		
		return result;
	}
	
	private void initType( String uuid ) {
		if (!nested) {
			type = dao.findType( uuid );
		} else {
			typeLink = findTypeLink( type, uuid );
		}
	}

	private Double getQuantityValue(Quantitative quantitative) {
		if( quantitative.getValue() == null )
			return null;
		
		return new Double( quantitative.getValue() );
	}
	
	private Date getTemporalValue(Temporal temporal) {
		if( temporal.getValue() == null )
			return null;
		
		return temporal.getValue().toGregorianCalendar().getTime();
	}
	
	private Unit findUnit( Type type, String uuid ) {
		UnitFinderVisitor visitor = new UnitFinderVisitor( uuid );
		type.accept( visitor );
		return visitor.getResult();
	}
	
	private Phenomenon findPhenomenon( Type type, String uuid ) {
		if ( ClassHelper.instanceOf( type, EnumeratedType.class )) {
			PhenomenonFinderVisitor visitor = new PhenomenonFinderVisitor( uuid );
			type.accept( visitor );
			return visitor.getResult();
		} else {
			return (uuid == null || uuid.isEmpty()) ? null : dao.findPhenomenon( uuid );
		}
	}
	
	private Type findType( String uuid ) {
		Type result = null;

		if ( !nested ) {
			if ( !uuid.equals( type.getUuid() )) 
				throw new RuntimeException( "Type for '" + uuid + "' not found" );
			result = type;
			
		} else {
			if ( !uuid.equals( typeLink.getUuid() ))
				throw new RuntimeException( "Type for '" + uuid + "' not found" );
			
			result = typeLink.getTarget();
		}
		
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
