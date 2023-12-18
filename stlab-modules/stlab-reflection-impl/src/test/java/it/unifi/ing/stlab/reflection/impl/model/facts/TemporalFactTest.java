package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TemporalFactTest {
	
	protected TemporalFactImpl fact;
	
	@Before
	public void setUp() {
		fact = FactFactory.createTemporal();
	}
 
	@Test
	public void testTextualType() {
		fact.setType( TypeFactory.createTextualType() );
		assertNotNull( fact.getType() );
	}

	@Test
	public void testIsEmpty() {
		fact.setDate( null );
		assertTrue( fact.isEmpty() );

		fact.setDate( new Date() );
		assertFalse( fact.isEmpty() );
	}

	@Test
	public void testSameAs() {
		TemporalType type = TypeFactory.createTemporalType(); 
		TemporalType otherType = TypeFactory.createTemporalType();
		
		TemporalFactImpl otherObservation = FactFactory.createTemporal();
		Date date = DateHelper.createDate( "2013-03-01" );
		
		fact.setType( type );
		assertTrue( fact.sameAs( fact ));

		fact.setDate( date );
		assertTrue( fact.sameAs( fact ));

		assertFalse( fact.sameAs( otherObservation) );
		
		otherObservation.setType( otherType );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setDate( date );
		assertFalse( fact.sameAs( otherObservation) );

		otherObservation.setType( type );
		assertTrue( fact.sameAs( otherObservation) );

		otherObservation.setDate( DateHelper.createDate( "2013-03-02" ) );
		assertFalse( fact.sameAs( otherObservation) );
		
		fact.setDate( null );
		assertFalse( fact.sameAs( otherObservation) );
	}
	
	@Test
	public void testCopy() {
		fact.setType( TypeFactory.createTextualType() );
		fact.setDate( new Date() );
		TemporalFactImpl copy = fact.copy();
		
		assertTrue( copy.sameAs( fact ));
		assertFalse( copy.equals( fact ));
	}
}
