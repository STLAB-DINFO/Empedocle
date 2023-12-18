package it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.empedocle.factory.health.coding.icd9cm.ICD9CMCodeFactory;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class ICD9CMCodeJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		ICD9CMTopicCode code = ICD9CMCodeFactory.createTopicCode();
		code.setCode( "code" );
		code.setDescription( "description" );
		
		code.setPhenomenon( PhenomenonFactory.createPhenomenon() );
		code.getPhenomenon().setName( "phenomenon" );
		
		entityManager.persist( code );

		uuid = code.getUuid();
	}
	
	@Test
	public void test() {
		ICD9CMTopicCode code = (ICD9CMTopicCode)
			entityManager
				.createQuery( 
					"select p " +
					" from ICD9CMCode p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( code );
		
		assertEquals( "code", code.getCode());
		assertEquals( "description", code.getDescription());
		assertEquals( "phenomenon", code.getPhenomenon().getName() );
		assertNull( code.getParent() );
		assertNull( code.getRoot() );
	}
	
}
