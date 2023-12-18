package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class UnitJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		Unit unit = UnitFactory.createUnit();
		unit.setName( "Metro" );
		unit.setSimbol( "m" );
		
		entityManager.persist( unit );
		uuid = unit.getUuid();
	}
	
	@Test
	public void testUnit() {
		Unit unit = (Unit)
			entityManager
				.createQuery( 
					"select u " +
					" from Unit u " +
					" where u.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( unit );
		assertEquals( "Metro", unit.getName() );
		assertEquals( "m", unit.getSimbol() );
	}
	
}
