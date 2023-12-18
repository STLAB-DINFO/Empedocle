package it.unifi.ing.stlab.reflection.lite.converter.jaxb;

import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.Composite;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.Qualitative;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.Quantitative;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.Temporal;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated.Textual;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class ReflectionToObjectVisitor implements FactVisitor {

	private Object result;
	private boolean nested;
	private TypeLink typeLink;
	
	public Object getResult() {
		return result;
	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		Textual object = new Textual();
		object.setType( uuid( fact.getType() ));
		object.setValue( fact.getText() );
		
		result = object;
	}

	@Override
	public void visitTemporal(TemporalFact fact) {
		try {
			Temporal object = new Temporal();
			object.setType( uuid( fact.getType() ));
			
			if( fact.getDate() != null ) {
				GregorianCalendar c = new GregorianCalendar();
				c.setTime( fact.getDate() );
				object.setValue( DatatypeFactory.newInstance().newXMLGregorianCalendar(c) );
			}
			
			result = object;
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException( e );
		}
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		Quantitative object = new Quantitative();
		object.setType( uuid( fact.getType() ));
		object.setUm( fact.getQuantity().getUnit().getUuid() );
		object.setValue( getFloatValue( fact ) );
		
		result = object;
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		Qualitative object = new Qualitative();
		object.setType( uuid( fact.getType() ));
		object.setValue( getPhenUuid( fact ) );
		
		result = object;
	}


	@Override
	public void visitComposite(CompositeFact fact) {
		boolean oldNested = nested;
		TypeLink oldTypeLink = typeLink;

		Composite object = new Composite();
		object.setType( uuid( fact.getType()));
		
		for ( FactLink link : fact.listChildrenOrdered() ) {
			nested = true;
			typeLink = link.getType();
			result = null;
			link.getTarget().accept( this );
			if ( result != null ) {
				object.getChildren().add( result );
			}
		}
		
		nested = oldNested;
		typeLink = oldTypeLink;
		result = object;
	}

	private String uuid( Type type ) {
		if ( nested ) {
			return typeLink.getUuid();
		} else {
			return type.getUuid();
		}
	}
	
	
	private String getPhenUuid(QualitativeFact fact) {
		return fact.getPhenomenon() == null ? null : fact.getPhenomenon().getUuid();
	}
	
	private Float getFloatValue(QuantitativeFact fact) {
		if( fact.getQuantity().getValue() == null )
			return null;
		
		return fact.getQuantity().getValue().floatValue();
	}
	
}
