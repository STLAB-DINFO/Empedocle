package it.unifi.ing.stlab.empedocle.model.health.coding.atc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.empedocle.factory.health.coding.atc.ATCCodeFactory;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class ATCCodeJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		ATCCode code = ATCCodeFactory.createAnatomicalMainGroupCode();
		code.setCode( "code" );
		code.setDescription( "description" );
		
		code.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		code.getPhenomenon().setName( "phenomenon" );
		
		entityManager.persist( code );

		uuid = code.getUuid();
	}
	
	@Test
	public void test() {
		ATCAnatomicalMainGroupCode code = (ATCAnatomicalMainGroupCode)
			entityManager
				.createQuery( 
					"select p " +
					" from ATCCode p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( code );
		
		assertEquals( "code", code.getCode());
		assertEquals( "description", code.getDescription());
		assertEquals( "phenomenon", code.getPhenomenon().getName() );
		assertNull( code.getParent() );
	}
	
}
