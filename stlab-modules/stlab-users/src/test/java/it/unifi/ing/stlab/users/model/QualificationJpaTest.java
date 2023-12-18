package it.unifi.ing.stlab.users.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class QualificationJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		Qualification qualification = QualificationFactory.createQualification();
		qualification.setName( "doctor" );
		qualification.setTimeRange( period );

		entityManager.persist( qualification );

		uuid = qualification.getUuid();
	}
	
	@Test
	public void testQualification() {
		Qualification qualification = (Qualification)
			entityManager
				.createQuery( 
					"select p " +
					" from Qualification p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( qualification );
		assertEquals( "doctor", qualification.getName() );
		assertEquals( period, qualification.getTimeRange() );
	}
	
}
