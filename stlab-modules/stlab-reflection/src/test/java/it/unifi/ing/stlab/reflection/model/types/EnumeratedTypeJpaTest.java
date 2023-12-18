package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class EnumeratedTypeJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		Phenomenon phenomenon = PhenomenonFactory.createPhenomenon();
		phenomenon.setName( "Febbre" );
		phenomenon.setTimeRange( period );
		entityManager.persist( phenomenon );
		
		EnumeratedType type = TypeFactory.createEnumeratedType();
		type.setName( "Anamnesi" );
		type.setDescription( "blah blah blah..." );
		type.addPhenomenon( phenomenon );
		type.setTimeRange( period );
		
		entityManager.persist( type );

		uuid = type.getUuid();
	}
	
	@Test
	public void testEnumeratedType() {
		EnumeratedType type = (EnumeratedType)
			entityManager
				.createQuery( 
					"select t " +
					" from EnumeratedType t " +
					" where t.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( type );
		
		assertEquals( "Anamnesi", type.getName() );
		assertEquals( "blah blah blah...", type.getDescription() );
		assertEquals( 1, type.getPhenomena().size() );
		assertEquals( period, type.getTimeRange() );
	}
	
}
