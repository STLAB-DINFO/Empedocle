package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;

import java.util.UUID;

import org.junit.Test;

public class OutputImageJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		OutputField out = new OutputField(uuid);
		out.setPath("Prova.Prova");
		out.setName("nome");
		
		entityManager.persist(out);
		
	}

	@Test
	public void testOutputImage(){
		OutputField result = (OutputField)entityManager
									.createQuery("select o from OutputField o where o.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
		assertEquals("Prova.Prova", result.getPath());
	}
	
}
