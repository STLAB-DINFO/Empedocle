package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class QuantitativeFactJpaTest extends PersistenceTest {

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
		
		Unit mu = UnitFactory.createUnit();
		entityManager.persist( mu );
		
		QuantitativeType quantitativeType = TypeFactory.createQuantitativeType();
		entityManager.persist( quantitativeType );
		
		QuantitativeFactImpl fact = factDao.createQuantitative( user, new Time( DateHelper.createDate( "2013-03-01")));
		fact.assignType( quantitativeType );
		fact.setQuantity( new Quantity( new Double( 123 ), mu ));
		fact.setTimeRange( period );
		
		entityManager.persist( fact.getOrigin() );
		entityManager.persist( fact );

		uuid = fact.getUuid();
	}
	
	@Test
	public void testQuantitativeFact() {
		QuantitativeFactImpl fact = entityManager
				.createQuery( 
					"select f from QuantitativeFactImpl f " +
					" where f.uuid = :uuid", QuantitativeFactImpl.class )
				.setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( fact );
		assertNotNull( fact.getType() );
		assertEquals( new Double( 123 ), fact.getQuantity().getValue() );
		assertNotNull( fact.getQuantity().getUnit() );
		assertEquals( period, fact.getTimeRange() );
	}
	
}
