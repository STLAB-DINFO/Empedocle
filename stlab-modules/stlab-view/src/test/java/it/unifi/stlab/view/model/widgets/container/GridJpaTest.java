package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;

import java.util.UUID;

import org.junit.Test;

public class GridJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sv1, sv2;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sv1 = new SubViewer(UUID.randomUUID().toString());
		sv2 = new SubViewer(UUID.randomUUID().toString());
		
		entityManager.persist(sv1);
		entityManager.persist(sv2);
		
		Grid container = new Grid(uuid);
		container.setName("nome");
		container.setCollapse(true);
		sv1.assignSource(container);
		sv2.assignSource(container);
		container.setOrientation(PanelOrientation.horizontal);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testGrid(){
		Grid result = (Grid)entityManager.createQuery("select g from Grid g where g.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(2, result.listChildren().size());
		assertEquals("nome", result.getName());
		assertTrue(result.getCollapse());
		assertTrue(result.listChildren().contains(sv1));
		assertTrue(result.listChildren().contains(sv2));
		assertEquals(PanelOrientation.horizontal, result.getOrientation());
	}
	
	
}
