package it.unifi.ing.stlab.reflection.lite.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;

import org.junit.Before;
import org.junit.Test;

public class QualitativeFactTest {
	
	protected QualitativeFactLite fact;
	
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

}
