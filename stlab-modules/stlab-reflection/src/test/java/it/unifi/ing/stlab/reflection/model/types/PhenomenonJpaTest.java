package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class PhenomenonJpaTest extends PersistenceTest {

	protected String uuid;
	protected String uuidNoTimeRange;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		Phenomenon phenomenon = PhenomenonFactory.createPhenomenon();
		phenomenon.setName( "Febbre" );
		phenomenon.setTimeRange( period );

		Phenomenon phenomenonNoTimeRange = PhenomenonFactory.createPhenomenon();
		phenomenonNoTimeRange.setName( "Prova TimeRange" );
		
		entityManager.persist( phenomenon );
		entityManager.persist( phenomenonNoTimeRange );
		
		uuid = phenomenon.getUuid();
		uuidNoTimeRange = phenomenonNoTimeRange.getUuid();
	}
	
	@Test
	public void testPhenomenon() {
		Phenomenon phenomenon = (Phenomenon)
			entityManager
				.createQuery( 
					"select p " +
					" from Phenomenon p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( phenomenon );
		assertEquals( "Febbre", phenomenon.getName() );
		assertEquals( period, phenomenon.getTimeRange() );
	}
	
	
	@Test
	public void testPhenomenonNoTimeRange() {
		Phenomenon phenomenon = (Phenomenon)
			entityManager
				.createQuery( 
					"select p " +
					" from Phenomenon p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuidNoTimeRange ).getSingleResult();
	
		assertNotNull( phenomenon );
		assertEquals( "Prova TimeRange", phenomenon.getName() );
		assertTrue(phenomenon.isValidAt(new Time( DateHelper.createDate( "2013-03-01"))));
	}
}
