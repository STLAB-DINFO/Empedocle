package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Report;

import java.util.UUID;

import org.junit.Test;

public class ReportJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sv1, sv2, sv3;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sv1 = new SubViewer(UUID.randomUUID().toString());
		sv2 = new SubViewer(UUID.randomUUID().toString());
		sv3 = new SubViewer(UUID.randomUUID().toString());
		
		entityManager.persist(sv1);
		entityManager.persist(sv2);
		entityManager.persist(sv3);
		
		Report container = new Report(uuid);
		container.setName("nome");
		sv1.assignSource(container);
		sv2.assignSource(container);
		sv3.assignSource(container);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testReport(){
		Report result = (Report)entityManager.createQuery("select r from Report r where r.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(3, result.listChildren().size());
		assertEquals("nome", result.getName());
		assertTrue(result.listChildren().contains(sv1));
		assertTrue(result.listChildren().contains(sv2));
		assertTrue(result.listChildren().contains(sv3));
	}
	
	
}
