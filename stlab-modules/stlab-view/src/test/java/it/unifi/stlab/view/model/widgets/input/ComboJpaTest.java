package it.unifi.stlab.view.model.widgets.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;

import java.util.UUID;

import org.junit.Test;

public class ComboJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		Combo input = new Combo(uuid);
		input.setName("nome");
		input.setRequired(true);
		
		entityManager.persist(input);
	}

	@Test
	public void testCombo(){
		Combo result = (Combo)entityManager
									.createQuery("select c from Combo c where c.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
		assertTrue(result.getRequired());
		
	}
	
}
