package it.unifi.stlab.view.model.widgets.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;

import java.util.UUID;

import org.junit.Test;

public class TextAreaJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		TextArea input = new TextArea(uuid);
		input.setName("nome");
		input.setRequired(true);
		input.setInputLength(20);
		
		entityManager.persist(input);
	}

	@Test
	public void testTextArea(){
		TextArea result = (TextArea)entityManager
										.createQuery("select t from TextArea t where t.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(0, result.listChildren().size());
		assertEquals("nome", result.getName());
		assertTrue(result.getInputLength().equals(20));
		assertTrue(result.getRequired());
		
	}
	
}
