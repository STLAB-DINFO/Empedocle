package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class CompositeTypeJpaTest extends PersistenceTest {

	protected String uuid;
	protected TimeRange period;
	 
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		TextualType textual1 = TypeFactory.createTextualType();
		textual1.setName( "Anamnesi" );
		textual1.setDescription( "blah blah blah..." );
		textual1.setTimeRange( period );
		entityManager.persist( textual1 );
		
		TextualType textual2 = TypeFactory.createTextualType();
		textual2.setName( "Diagnosi" );
		textual2.setDescription( "blah blah blah..." );
		textual2.setTimeRange( period );
		entityManager.persist( textual2 );
		
		
		CompositeType type = TypeFactory.createCompositeType();
		type.setName( "Altezza" );
		type.setDescription( "blah blah blah..." );
		type.setTimeRange( period );
		entityManager.persist( type );

		
		TypeLink link1 = TypeLinkFactory.createLink();
		link1.assignSource( type );
		link1.assignTarget( textual1 );
		entityManager.persist( link1 );
		
		
		TypeLink link2 = TypeLinkFactory.createLink();
		link2.assignSource( type );
		link2.assignTarget( textual2 );
		entityManager.persist( link2 );

		uuid = type.getUuid();
	}

	@Test
	public void testCompositeType() {
		CompositeType type = (CompositeType)
			entityManager
				.createQuery( 
					"select t " +
					" from CompositeType t " +
					" where t.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( type );
		
		assertEquals( "Altezza", type.getName() );
		assertEquals( "blah blah blah...", type.getDescription() );
		assertEquals( period, type.getTimeRange() );
		assertEquals( 2, type.listChildren().size() );
		assertEquals( 3, type.listDescendents().size() );
	}
	
}
