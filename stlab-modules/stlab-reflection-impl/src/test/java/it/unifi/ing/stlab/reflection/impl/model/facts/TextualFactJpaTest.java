package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;

public class TextualFactJpaTest extends PersistenceTest {

	protected Long typeId;
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
		
		TextualType textualType = TypeFactory.createTextualType();
		textualType.setName( "Textual Type" );
		entityManager.persist( textualType );
		
		typeId = textualType.getId();
		
		TextualFactImpl fact = factDao.createTextual( user, new Time( DateHelper.createDate( "2013-03-01")));
		fact.assignType( textualType );
		fact.setText( "text" );
		fact.setTimeRange( period );

		entityManager.persist( fact.getOrigin() );
		entityManager.persist( fact );

		uuid = fact.getUuid();
	}
	
	@Test
	public void testTextualFact() {
		TextualFactImpl fact = entityManager
				.createQuery( 
					"select f from TextualFactImpl f " +
					" where f.uuid = :uuid", TextualFactImpl.class ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( fact );
		assertNotNull( fact.getText() );
		assertNotNull( fact.getType() );
		assertEquals( "text", fact.getText() );
		assertEquals( period, fact.getTimeRange() );
	}
	
	@Test
	public void testProxiedInstanceOf() {
		Type type = entityManager.getReference( Type.class, typeId );
		assertTrue( type instanceof HibernateProxy );
		assertNotNull( type );
		assertTrue( ClassHelper.instanceOf( type, TextualType.class ));
	}

	@Test
	public void testProxiedCastTo() {
		Type type = entityManager.getReference( Type.class, typeId );
		
		assertNotNull( type );
		assertTrue( type instanceof HibernateProxy );
		assertNotNull( ClassHelper.cast( type, TextualType.class).getId() );
	}
}
