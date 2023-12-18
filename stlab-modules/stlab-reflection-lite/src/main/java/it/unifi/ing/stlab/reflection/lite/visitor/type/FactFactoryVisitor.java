package it.unifi.ing.stlab.reflection.lite.visitor.type;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.lite.model.facts.FactLite;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class FactFactoryVisitor implements TypeVisitor {
	
	private Fact result;

	public FactFactoryVisitor() {
		result = null;
	}
	
	public Fact getResult() {
		return result;
	}

	@Override
	public void visitTextualType(TextualType type) {
		result = FactFactory.createTextual();
		basicVisit( type );
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		result = FactFactory.createQualitative();
		basicVisit( type );
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		result = FactFactory.createQualitative();
		basicVisit( type );
	}
	
	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		result = FactFactory.createQuantitative();
		basicVisit( type );
	}
	
	@Override
	public void visitTemporalType(TemporalType type) {
		result = FactFactory.createTemporal();
		basicVisit( type );
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		result = FactFactory.createComposite();
		basicVisit( type );
		
		for ( TypeLink typeLink : type.listChildren() ) {
			if ( typeLink.getMin() != null && typeLink.getTarget() != null ) {
				
				for ( int i = 0; i < typeLink.getMin(); i ++ ) {
					FactFactoryVisitor visitor = new FactFactoryVisitor();
					typeLink.getTarget().accept(visitor);
					
					// Assign defaultValue
					visitor.getResult().assignDefaultValue( typeLink.getDefaultValue() );
					
					
					FactLink factLink = FactLinkFactory.insertLink( ClassHelper.cast( result, FactLite.class ),  
																ClassHelper.cast( visitor.getResult(), FactLite.class ) );
					factLink.setType(typeLink);
					factLink.setPriority( new Long(i) );
					
				}
			}
		}
	}
	
	private void basicVisit(Type type) {
		result.assignType( type );
		result.setStatus(FactStatus.DRAFT);
	}
	
}