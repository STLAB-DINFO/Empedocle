package it.unifi.stlab.view.model.widgets.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;

import java.util.UUID;

import org.junit.Test;

public class InputListJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sottoVista;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sottoVista = new SubViewer(UUID.randomUUID().toString());
		
		entityManager.persist(sottoVista);
		
		InputList input = new InputList(uuid);
		input.setName("nome");
		input.setOrientation(PanelOrientation.horizontal);
		sottoVista.assignSource(input);
		
		entityManager.persist(input);
		
	}

	@Test
	public void testInputList(){
		InputList result = (InputList)entityManager
								.createQuery("select o from InputList o where o.uuid = :uuid")
								.setParameter("uuid", uuid)
								.getSingleResult();
		
		assertNotNull(result);
		assertEquals(1, result.listChildren().size());
		assertTrue(result.listChildren().contains(sottoVista));
		assertEquals("nome", result.getName());
		assertEquals(PanelOrientation.horizontal, result.getOrientation());
		assertFalse(result.getRequired());
	}
	
}
