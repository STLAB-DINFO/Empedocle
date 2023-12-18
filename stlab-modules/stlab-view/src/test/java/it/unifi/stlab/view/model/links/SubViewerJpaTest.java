package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Test;

public class SubViewerJpaTest extends PersistenceTest {

	protected String uuid;
	protected TypeSelector selector;
	protected Grid vista1; 
	protected Label vista2;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		SubViewer sv = new SubViewer(uuid);
		
		selector = new TypeSelector(UUID.randomUUID().toString());
		sv.setSelector(selector);
		entityManager.persist(selector);
		
		vista1 = new Grid(UUID.randomUUID().toString());
		vista2 = new Label(UUID.randomUUID().toString());
		entityManager.persist(vista1);
		entityManager.persist(vista2);

		sv.assignSource(vista1);
		sv.assignTarget(vista2);
		
		entityManager.persist(sv);
		
	}

	@Test
	public void testSottoVista(){
		SubViewer result = (SubViewer)entityManager
								.createQuery("select s from SubViewer s where s.uuid = :uuid")
								.setParameter("uuid", uuid)
								.getSingleResult();
		
		assertNotNull(result);
		assertNotNull(result.getSelector());
		assertNotNull(result.getSource());
		assertNotNull(result.getTarget());
		assertEquals(selector, result.getSelector());
		assertEquals(vista1, result.getSource());
		assertEquals(vista2, result.getTarget());
													
	}
	
}
