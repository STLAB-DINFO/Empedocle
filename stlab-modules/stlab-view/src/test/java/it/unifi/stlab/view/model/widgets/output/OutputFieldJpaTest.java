package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;

import java.util.UUID;

import org.junit.Test;

public class OutputFieldJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		OutputImage out = new OutputImage(uuid);
		out.setImagePath("/prova/prova.png");
		out.setName("nome");
		
		entityManager.persist(out);
		
	}

	@Test
	public void testOutputField(){
		OutputImage result = (OutputImage)entityManager
									.createQuery("select o from OutputImage o where o.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
		assertEquals("/prova/prova.png", result.getImagePath());
	}
	
}
