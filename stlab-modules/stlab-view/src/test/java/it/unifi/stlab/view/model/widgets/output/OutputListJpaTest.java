package it.unifi.stlab.view.model.widgets.output;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;

import java.util.UUID;

import org.junit.Test;

public class OutputListJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sottoVista;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sottoVista = new SubViewer(UUID.randomUUID().toString());
		
		entityManager.persist(sottoVista);
		
		OutputList out = new OutputList(uuid);
		out.setName("nome");
		out.setOrientation(PanelOrientation.horizontal);
		sottoVista.assignSource(out);
		
		entityManager.persist(out);
		
	}

	@Test
	public void testOutputList(){
		OutputList result = (OutputList)entityManager
									.createQuery("select o from OutputList o where o.uuid = :uuid")
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull(result);
		assertEquals(1, result.listChildren().size());
		assertTrue(result.listChildren().contains(sottoVista));
		assertEquals("nome", result.getName());
		assertEquals(PanelOrientation.horizontal, result.getOrientation());
	}
	
}
