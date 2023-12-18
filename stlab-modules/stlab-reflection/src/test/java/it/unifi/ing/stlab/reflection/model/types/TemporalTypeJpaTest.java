package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.commons.util.TimeFormat;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class TemporalTypeJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		TemporalType type = TypeFactory.createTemporalType();
		type.setName( "Data di morte" );
		type.setDescription( "blah blah blah..." );
		type.setTimeRange( period );
		type.setType( TimeFormat.DATETIME );
		entityManager.persist( type );

		uuid = type.getUuid();
	}
	
	@Test
	public void testTextualType() {
		TemporalType type = (TemporalType)
			entityManager
				.createQuery( 
					"select ot " +
					" from TemporalType ot " +
					" where ot.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( type );
		
		assertEquals( "Data di morte", type.getName() );
		assertEquals( "blah blah blah...", type.getDescription() );
		assertEquals( period, type.getTimeRange() );
	}
	
}
