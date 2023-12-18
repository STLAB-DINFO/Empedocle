package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;

import org.junit.Before;
import org.junit.Test;

public class QualitativeFactTest {
	
	protected QualitativeFactImpl fact;
	
	@Before
	public void setUp() {
		fact = FactFactory.createQualitative();
	}

	@Test
	public void testQueriedType() {
		fact.setType( TypeFactory.createQueriedType() );
		assertNotNull( fact.getType() );
	}
	
	@Test
	public void testIsEmpty() {
		fact.setPhenomenon( null );
		assertTrue( fact.isEmpty() );

		fact.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		assertFalse( fact.isEmpty() );
	}

	@Test
	public void testSameAs() {
		QualitativeType type = TypeFactory.createEnumeratedType(); 
		QualitativeType otherType = TypeFactory.createQueriedType();
		
		QualitativeFactImpl otherObservation = FactFactory.createQualitative();
	
		fact.setType( type );
		assertTrue( fact.sameAs( fact ));

		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		fact.setPhenomenon( ph );
		assertTrue( fact.sameAs( fact ));

		assertFalse( fact.sameAs( otherObservation) );
		
		otherObservation.setType( otherType );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setPhenomenon( ph );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setType( type );
		assertTrue( fact.sameAs( otherObservation) );

		otherObservation.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		assertFalse( fact.sameAs( otherObservation) );
	}

	@Test
	public void testCopy() {
		fact.setType( TypeFactory.createEnumeratedType() );
		fact.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		QualitativeFactImpl copy = fact.copy();
		
		assertTrue( copy.sameAs( fact ));
		assertFalse( copy.equals( fact ));
	}
}
