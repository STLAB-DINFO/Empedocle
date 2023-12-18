package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;

import org.junit.Before;
import org.junit.Test;

public class EnumeratedTypeTest {

	protected EnumeratedType type;
	protected Phenomenon measurementUnit;
	
	@Before
	public void setUp() {
		type = TypeFactory.createEnumeratedType();
		measurementUnit = PhenomenonFactory.createPhenomenon();
	}
	
	
	@Test
	public void testGetPhenomena() {
		assertEquals( 0, type.getPhenomena().size() );
	}
	
	@Test
	public void testAddPhenomenon() {
		type.addPhenomenon( measurementUnit );
		
		assertEquals( 1, type.getPhenomena().size() );
		assertTrue( type.getPhenomena().contains( measurementUnit ));
	}

	@Test
	public void testAddPhenomenonDouble() {
		type.addPhenomenon( measurementUnit );
		type.addPhenomenon( measurementUnit );
		
		assertEquals( 1, type.getPhenomena().size() );
		assertTrue( type.getPhenomena().contains( measurementUnit ));
	}

	@Test
	public void testAddPhenomenonNull() {
		type.addPhenomenon( null );
		
		assertEquals( 0, type.getPhenomena().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testPhenomenonNoDirectAdd() {
		type.listPhenomena().add( measurementUnit );
	}
	
	@Test
	public void testRemovePhenomenon() {
		type.addPhenomenon( measurementUnit );
		type.removePhenomenon( measurementUnit );

		assertEquals( 0, type.getPhenomena().size() );
	}
	
}
