package it.unifi.stlab.view.model.links;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.UUID;

import org.junit.Test;

public class TypeSelectorJpaTest extends PersistenceTest {

	protected String uuid;
	protected TypeLink typeLink;
	protected TypeSelector selector2;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		TypeSelector selector = new TypeSelector(uuid);
		selector2 = new TypeSelector(UUID.randomUUID().toString());
		
		typeLink = TypeLinkFactory.createLink();
		entityManager.persist(typeLink);
		
		selector.setTypeLink(typeLink);
		selector.assignNext(selector2);

		entityManager.persist(selector2);
		entityManager.persist(selector);
	}

	@Test
	public void testTipoOsservazioneSelector(){
		TypeSelector result = (TypeSelector)entityManager
											.createQuery("select t from TypeSelector t where t.uuid = :uuid")
											.setParameter("uuid", uuid)
											.getSingleResult();
		
		assertNotNull(result);
		assertNotNull(result.getNext());
		assertNotNull(result.getTypeLink());
		assertEquals(selector2, result.getNext());
		assertEquals(typeLink, result.getTypeLink());
		
	}
	
	
}
