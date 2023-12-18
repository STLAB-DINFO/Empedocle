package it.unifi.stlab.view.model.widgets.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;

import java.util.UUID;

import org.junit.Test;

public class InputTextJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		InputText input = new InputText(uuid);
		input.setName("nome");
		input.setRequired(true);
		input.setInputLength(20);
		
		entityManager.persist(input);
	}

	@Test
	public void testInputText(){
		InputText result = (InputText)entityManager
										.createQuery("select i from InputText i where i.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals("nome", result.getName());
		assertTrue(result.getInputLength().equals(20));
		assertTrue(result.getRequired());
		
	}
	
}
