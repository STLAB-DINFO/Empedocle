package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.UUID;

import org.junit.Test;

public class QuantitativeTypeJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		Unit unit = UnitFactory.createUnit();
		unit.setName( "Metro" );
		unit.setSimbol( "m" );
		entityManager.persist( unit );
		
		UnitUse unitUse = new UnitUse( UUID.randomUUID().toString() );
		unitUse.setDigits( 8 );
		unitUse.setDecimals( 3 );
		entityManager.persist(unitUse );
		
		
		QuantitativeType type = TypeFactory.createQuantitativeType();
		type.setName( "Altezza" );
		type.setDescription( "blah blah blah..." );
		type.setTimeRange( period );
		type.addUnit( unitUse );
		
		
		entityManager.persist( type );
		uuid = type.getUuid();
	}
	
	@Test
	public void testQuantitativeType() {
		QuantitativeType type = (QuantitativeType)
			entityManager
				.createQuery( 
					"select t " +
					" from QuantitativeType t " +
					" where t.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( type );
		
		assertEquals( "Altezza", type.getName() );
		assertEquals( "blah blah blah...", type.getDescription() );
		assertEquals( period, type.getTimeRange() );
		assertEquals( 1, type.getUnits().size() );
	}
	
}
