package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

import javax.persistence.EntityManager;

public class FactPersistVisitor implements FactVisitor {

	private final EntityManager entityManager;

	public FactPersistVisitor( EntityManager entityManager ) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void visitTextual(TextualFact fact) {
		basePersist( fact );
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		basePersist( fact );
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		basePersist( fact );
	}
	
	@Override
	public void visitTemporal(TemporalFact fact) {
		basePersist( fact );
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		basePersist( fact );

		for ( FactLink link : fact.listChildren() ) {
			link.getTarget().accept( this );
			entityManager.persist( link );
		}
	}
	
	private void basePersist( Fact fact ) {
		entityManager.persist( fact );

		FactImpl factImpl = ClassHelper.cast( fact, FactImpl.class );
		
		if ( factImpl.getOrigin() != null ) {
			entityManager.persist( factImpl.getOrigin() );
		}
		if ( factImpl.getDestination() != null ) {
			entityManager.persist( factImpl.getDestination() );
		}
	}
	
}
