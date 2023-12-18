package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.Icd9cmFactory;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class Icd9cmJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		Icd9cm icd9cm = Icd9cmFactory.createIcd9cm();
		icd9cm.setCode( "code" );
		icd9cm.setDescription( "description" );
		
		icd9cm.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		icd9cm.getPhenomenon().setName( "phenomenon" );
		
		entityManager.persist( icd9cm );

		uuid = icd9cm.getUuid();
	}
	
	@Test
	public void testIcd9cm() {
		Icd9cm icd9cm = (Icd9cm)
			entityManager
				.createQuery( 
					"select p " +
					" from Icd9cm p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( icd9cm );
		
		assertEquals( "code", icd9cm.getCode());
		assertEquals( "description", icd9cm.getDescription());
		assertEquals( "phenomenon", icd9cm.getPhenomenon().getName() );
	}
	
}
