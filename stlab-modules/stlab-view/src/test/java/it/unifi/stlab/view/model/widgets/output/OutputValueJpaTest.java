package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.UUID;

import org.junit.Test;

public class OutputValueJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		OutputValue out = new OutputValue(uuid);
		out.setName("nome");
		
		entityManager.persist(out);
		
	}

	@Test
	public void testOutputValue(){
		OutputValue result = (OutputValue)entityManager
									.createQuery("select o from OutputValue o where o.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
	}
	
}
