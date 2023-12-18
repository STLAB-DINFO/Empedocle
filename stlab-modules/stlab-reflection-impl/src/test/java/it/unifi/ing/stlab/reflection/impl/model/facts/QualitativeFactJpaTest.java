package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class QualitativeFactJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	protected FactManager factDao;
	
	@Override
	protected void insertData() throws Exception {
		factDao = new FactManager();

		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		User user = UserFactory.createUser();
		entityManager.persist( user );

		Phenomenon phenomenon = PhenomenonFactory.createPhenomenon();
		entityManager.persist( phenomenon );
		
		QualitativeType textualType = TypeFactory.createEnumeratedType();
		entityManager.persist( textualType );
		
		QualitativeFactImpl fact = factDao.createQualitative( user, new Time( DateHelper.createDate( "2013-03-01")));
		fact.assignType( textualType );
		fact.setPhenomenon( phenomenon );
		fact.setTimeRange( period );

		entityManager.persist( fact.getOrigin() );
		entityManager.persist( fact );

		uuid = fact.getUuid();
	}
	
	@Test
	public void testQualitativeFact() {
		QualitativeFactImpl fact =  entityManager
				.createQuery( 
					"select f from QualitativeFactImpl f " +
					" where f.uuid = :uuid", QualitativeFactImpl.class )
				.setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( fact );
		assertNotNull( fact.getType() );
		assertNotNull( fact.getPhenomenon() );
		assertEquals( period, fact.getTimeRange() );
	}
	
}
