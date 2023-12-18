package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Date;

import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;

public class TemporalFactJpaTest extends PersistenceTest {

	protected Long typeId;
	protected String uuid;
	protected TimeRange period;
	protected FactManager factDao;
	protected Date date;
	
	@Override
	protected void insertData() throws Exception {
		factDao = new FactManager();
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		User user = UserFactory.createUser();
		entityManager.persist( user );
		
		TemporalType dateType = TypeFactory.createTemporalType();
		dateType.setName( "Dat5e Type" );
		entityManager.persist( dateType );
		
		typeId = dateType.getId();
		
		date = DateHelper.createDate( "2013-11-10");
		
		TemporalFactImpl fact = factDao.createTemporal( user, new Time( DateHelper.createDate( "2013-03-01")));
		fact.assignType( dateType );
		fact.setDate( date );
		fact.setTimeRange( period );

		entityManager.persist( fact.getOrigin() );
		entityManager.persist( fact );

		uuid = fact.getUuid();
	}
	
	@Test
	public void testTextualFact() {
		TemporalFactImpl fact = entityManager
				.createQuery( 
					"select f from TemporalFactImpl f " +
					" where f.uuid = :uuid", TemporalFactImpl.class )
				.setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( fact );
		assertNotNull( fact.getDate() );
		assertNotNull( fact.getType() );
		assertEquals( date, fact.getDate() );
		assertEquals( period, fact.getTimeRange() );
	}
	
	@Test
	public void testProxiedInstanceOf() {
		Type type = entityManager.getReference( Type.class, typeId );
		assertTrue( type instanceof HibernateProxy );
		assertNotNull( type );
		assertTrue( ClassHelper.instanceOf( type, TemporalType.class ));
	}

	@Test
	public void testProxiedCastTo() {
		Type type = entityManager.getReference( Type.class, typeId );
		
		assertNotNull( type );
		assertTrue( type instanceof HibernateProxy );
		assertNotNull( ClassHelper.cast( type, TemporalType.class).getId() );
	}
}
