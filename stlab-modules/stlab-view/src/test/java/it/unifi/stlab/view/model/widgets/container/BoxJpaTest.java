package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Box;

import java.util.UUID;

import org.junit.Test;

public class BoxJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sv1;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sv1 = new SubViewer(UUID.randomUUID().toString());

		entityManager.persist(sv1);
		 
		Box container = new Box(uuid);
		container.setCollapse(true);
		container.setName("nome");
		sv1.assignSource(container);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testBox(){
		Box result = (Box)entityManager.createQuery("select b from Box b where b.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(1, result.listChildren().size());
		assertEquals("nome", result.getName());
		assertTrue(result.getCollapse());
		assertTrue(result.listChildren().contains(sv1));
		
	}
	
	
}
