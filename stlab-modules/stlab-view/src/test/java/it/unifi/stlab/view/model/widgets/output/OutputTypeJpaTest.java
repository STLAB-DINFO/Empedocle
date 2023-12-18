package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;

import java.util.UUID;

import org.junit.Test;

public class OutputTypeJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		OutputType out = new OutputType(uuid);
		out.setName("nome");
		
		entityManager.persist(out);
		
	}

	@Test
	public void testOutputType(){
		OutputType result = (OutputType)entityManager
									.createQuery("select o from OutputType o where o.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
	}
	
}
