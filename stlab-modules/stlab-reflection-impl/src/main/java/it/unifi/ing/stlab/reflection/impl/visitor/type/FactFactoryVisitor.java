package it.unifi.ing.stlab.reflection.impl.visitor.type;

import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
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
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public class FactFactoryVisitor implements TypeVisitor {
	private FactManager factory;
	private FactLinkFactory linkFactory; 
	private FactImpl result;
	private User user;
	private Time time;

	public FactFactoryVisitor( User user, Time time ) {
		result = null;
		this.factory = new FactManager();
		this.linkFactory = new FactLinkFactory();
		this.user = user;
		this.time = time;
	}
	
	public FactImpl getResult() {
		return result;
	}

	@Override
	public void visitTextualType(TextualType type) {
		result = factory.createTextual( user, time );
		basicVisit( type );
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		result = factory.createQualitative( user, time );
		basicVisit( type );
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		result = factory.createQualitative( user, time );
		basicVisit( type );
	}
	
	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		result = factory.createQuantitative( user, time );
		basicVisit( type );
	}
	
	@Override
	public void visitTemporalType(TemporalType type) {
		result = factory.createTemporal( user, time );
		basicVisit( type );
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		result = factory.createComposite( user, time );
		basicVisit( type );
		
		for ( TypeLink typeLink : type.listChildren() ) {
			if ( typeLink.getMin() != null && typeLink.getTarget() != null ) {
				
				for ( int i = 0; i < typeLink.getMin(); i ++ ) {
					FactFactoryVisitor visitor = new FactFactoryVisitor( user, time );
					typeLink.getTarget().accept(visitor);
					
					// Assign defaultValue
					visitor.getResult().assignDefaultValue( typeLink.getDefaultValue() );
					
					
					FactLink factLink = linkFactory.insertLink( result, visitor.getResult() );
					factLink.setType(typeLink);
					
				}
			}
		}
	}
	
	private void basicVisit(Type type) {
		result.assignType( type );
		result.setStatus(FactStatus.DRAFT);
	}
	
}