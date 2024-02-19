package it.unifi.ing.stlab.reflection.visitor.fact;

import it.unifi.ing.stlab.commons.random.RandomGenerator;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class FactRandomInitializerVisitor implements FactVisitor {

	private final RandomGenerator randomGenerator;
	private static final String characters = "abcdefghijklmnopqrstuvwxyz";
	
	private PhenomenonDao phenomenonDAO;
	
	public FactRandomInitializerVisitor() {
		randomGenerator = new RandomGenerator();
	}
	
	@Override
	public void visitTextual(TextualFact oss) {
		int length = randomGenerator.random( 1, 20 );
		
		StringBuffer buffer = new StringBuffer();
		for ( int i = 0; i < length; i++ ) {
			int c = randomGenerator.random( 0, characters.length() -1 );
			buffer.append( characters.charAt( c));
		}
		
		oss.setText(buffer.toString());
	}

	@Override
	public void visitQuantitative(QuantitativeFact oss) {
		Quantity q = new Quantity();
		UnitUse u = (random( ((QuantitativeType)oss.getType()).listUnits()));
		q.setUnit(u.getUnit());
		q.setValue( new Double( randomGenerator.random( 1, 100 )));
		
		oss.setQuantity(q);
	}

	@Override
	public void visitQualitative(QualitativeFact oss) {
		oss.setPhenomenon( random( phenomenonDAO.findByFact(oss, new Date()) ) );
	}

	@Override
	public void visitTemporal(TemporalFact oss) {
		oss.setDate( new Date() );
	}

	@Override
	public void visitComposite(CompositeFact oss) {
		for ( FactLink link : oss.listActiveLinks() ) {
			if(link.getTarget() != null)
				link.getTarget().accept( this );
		}
	}

	private <T> T random( Collection<T> collection ) {
		int index = 1;
		
		if ( collection.size() > 1 ) {
			index = randomGenerator.random( 1, collection.size());
		}

		Iterator<T> iterator = collection.iterator();
		
		T result = null;
		for ( int i = 0; i < index; i++ ) {
			result = iterator.next();
		}
		return result;
	}

	
	public void setPhenomenonDAO(PhenomenonDao phenomenonDAO) {
		this.phenomenonDAO = phenomenonDAO;
	}
	
}
