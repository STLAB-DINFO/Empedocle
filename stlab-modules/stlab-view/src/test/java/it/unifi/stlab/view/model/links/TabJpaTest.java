package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.UUID;

import org.junit.Test;

public class TabJpaTest extends PersistenceTest {

	protected String uuid;
	protected TypeSelector selector;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		selector = new TypeSelector(UUID.randomUUID().toString());
		entityManager.persist(selector);
		
		Tab container = new Tab(uuid);
		container.setLabel("label");
		container.setSelector(selector);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testTab(){
		Tab result = (Tab)entityManager.createQuery("select t from Tab t where t.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals("label", result.getLabel());
		assertEquals(selector, result.getSelector());
	}
	
	
}
