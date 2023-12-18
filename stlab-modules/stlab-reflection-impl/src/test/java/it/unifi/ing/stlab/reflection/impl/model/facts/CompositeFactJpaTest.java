package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class CompositeFactJpaTest extends PersistenceTest {

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
		
		CompositeType compositeType = TypeFactory.createCompositeType();
		entityManager.persist( compositeType );
		
		CompositeFactImpl fact = factDao.createComposite( user, new Time( DateHelper.createDate( "2013-03-01")));
		fact.assignType( compositeType );
		fact.setTimeRange( period );

		entityManager.persist( fact.getOrigin() );
		entityManager.persist( fact );

		uuid = fact.getUuid();
	}
	
	@Test
	public void testCompositeFact() {
		CompositeFactImpl fact =
			entityManager
				.createQuery( 
					"select f from CompositeFactImpl f " +
					" where f.uuid = :uuid", CompositeFactImpl.class )
				.setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( fact );
		assertNotNull( fact.getType() );
		assertEquals( period, fact.getTimeRange() );
	}
	
}
