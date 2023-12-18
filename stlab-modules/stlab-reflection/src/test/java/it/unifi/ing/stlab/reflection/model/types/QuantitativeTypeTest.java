package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;

import org.junit.Before;
import org.junit.Test;

public class QuantitativeTypeTest {

	protected QuantitativeType type;
	protected UnitUse unitUse;
	
	@Before
	public void setUp() {
		type = TypeFactory.createQuantitativeType();
		
		unitUse = UnitUseFactory.createUnitUse();
		unitUse.setUnit( UnitFactory.createUnit());
	}
	
	
	@Test
	public void testGetUnits() {
		assertEquals( 0, type.getUnits().size() );
	}
	
	@Test
	public void testAddUnit() {
		type.addUnit( unitUse );
		
		assertEquals( 1, type.getUnits().size() );
		assertTrue( type.getUnits().contains( unitUse ));
		assertEquals( type, unitUse.getType() );
	}

	@Test
	public void testAddUnitDouble() {
		type.addUnit( unitUse );
		type.addUnit( unitUse );
		
		assertEquals( 1, type.getUnits().size() );
		assertTrue( type.getUnits().contains( unitUse ));
	}

	@Test
	public void testChangeUnit() {
		QuantitativeType other = TypeFactory.createQuantitativeType();
		
		other.addUnit( unitUse );
		type.addUnit( unitUse );
		
		assertEquals( 0, other.getUnits().size() );
		assertEquals( 1, type.getUnits().size() );
		assertTrue( type.getUnits().contains( unitUse ));
		assertEquals( type, unitUse.getType() );
	}
	
	@Test
	public void testAddUnitNull() {
		type.addUnit( null );
		
		assertEquals( 0, type.getUnits().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testUnitsNoDirectAdd() {
		type.listUnits().add( unitUse );
	}
	
	@Test
	public void testRemoveUnit() {
		type.addUnit( unitUse );
		type.removeUnit( unitUse );

		assertEquals( 0, type.getUnits().size() );
		assertNull( unitUse.getType() );
	}

	@Test
	public void testClearUnit() {
		type.addUnit( unitUse );
		type.clearUnits();

		assertEquals( 0, type.getUnits().size() );
		assertNull( unitUse.getType() );
	}
	
}
