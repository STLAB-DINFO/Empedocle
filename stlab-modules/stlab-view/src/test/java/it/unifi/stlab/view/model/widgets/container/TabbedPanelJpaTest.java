package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;

import java.util.UUID;

import org.junit.Test;

public class TabbedPanelJpaTest extends PersistenceTest {

	protected String uuid;
	protected Tab tab1, tab2;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		tab1 = new Tab(UUID.randomUUID().toString());
		tab2 = new Tab(UUID.randomUUID().toString());
		
		entityManager.persist(tab1);
		entityManager.persist(tab2);
		
		TabbedPanel container = new TabbedPanel(uuid);
		container.setName("nome");
		tab1.assignSource(container);
		tab2.assignSource(container);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testTabbedPanel(){
		TabbedPanel result = (TabbedPanel)entityManager.createQuery("select t from TabbedPanel t where t.uuid = :uuid")
													.setParameter("uuid", uuid)
													.getSingleResult();
		
		assertNotNull(result);
		assertEquals(2, result.listChildren().size());
		assertEquals("nome", result.getName());
		assertTrue(result.listChildren().contains(tab1));
		assertTrue(result.listChildren().contains(tab2));
	}
	
	
}
