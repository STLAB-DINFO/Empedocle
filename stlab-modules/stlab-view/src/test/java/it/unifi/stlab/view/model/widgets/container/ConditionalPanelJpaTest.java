package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.commons.util.Operator;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;

import java.util.UUID;

import org.junit.Test;

public class ConditionalPanelJpaTest extends PersistenceTest {

	protected String uuid;
	protected SubViewer sv;
	protected Phenomenon f1, f2;
	protected TypeSelector sel1, sel2, sel3;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		sv = new SubViewer(UUID.randomUUID().toString());
		
		entityManager.persist(sv);
		
		f1 = new Phenomenon(UUID.randomUUID().toString());
		f2 = new Phenomenon(UUID.randomUUID().toString());
		
		entityManager.persist(f1);
		entityManager.persist(f2);
		
		sel1 = new TypeSelector(UUID.randomUUID().toString());
		sel2 = new TypeSelector(UUID.randomUUID().toString());
		sel3 = new TypeSelector(UUID.randomUUID().toString());
		
		entityManager.persist(sel1);
		entityManager.persist(sel2);
		
		ConditionalPanel container = new ConditionalPanel(uuid);
		container.setName("nome");
		container.addFenomeno(f1);
		container.addFenomeno(f2);
		container.addSelector(sel1);
		container.addSelector(sel2);
		container.addOperator("and");
		
		container.setClear(true);
		container.setClearSelector(sel3);
		
		container.addClearSelector( sel2 );
		container.addClearSelector( sel3 );
		
		sv.assignSource(container);
		
		entityManager.persist(container);
	}
	

	@Test
	public void testConditionalPanel(){
		ConditionalPanel result = (ConditionalPanel)entityManager
										.createQuery("select c from ConditionalPanel c where c.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(1, result.listChildren().size());
		assertEquals(2, result.getPhenomena().size());
		assertEquals(1, result.getOperators().size());
		assertEquals(2, result.getSelectors().size());
		
		assertEquals("nome", result.getName());
		
		assertTrue(result.listChildren().contains(sv));
		
		assertEquals(f1, result.getPhenomena().get(0));
		assertEquals(f2, result.getPhenomena().get(1));
		
		assertEquals(sel1, result.getSelectors().get(0));
		assertEquals(sel2, result.getSelectors().get(1));
		
		assertEquals(Operator.and, result.getOperators().get(0));
		
		assertEquals(true, result.isClear());
		assertEquals(sel3, result.getClearSelector());
		
	}
	
	@Test
	public void testConditionalPanel_multipleClearSelectors(){
		ConditionalPanel result = (ConditionalPanel)entityManager
										.createQuery("select c from ConditionalPanel c where c.uuid = :uuid")
										.setParameter("uuid", uuid)
										.getSingleResult();
		
		assertNotNull(result);
		assertEquals(1, result.listChildren().size());
		assertEquals(2, result.getPhenomena().size());
		assertEquals(1, result.getOperators().size());
		assertEquals(2, result.getSelectors().size());
		
		assertEquals("nome", result.getName());
		
		assertTrue(result.listChildren().contains(sv));
		
		assertEquals(f1, result.getPhenomena().get(0));
		assertEquals(f2, result.getPhenomena().get(1));
		
		assertEquals(sel1, result.getSelectors().get(0));
		assertEquals(sel2, result.getSelectors().get(1));
		
		assertEquals(Operator.and, result.getOperators().get(0));
		
		assertEquals(true, result.isClear());
		assertEquals(sel3, result.getClearSelector());
		
		assertEquals(2, result.getClearSelectors().size());
		assertEquals(sel2, result.getClearSelectors().get( 0 ));
		assertEquals(sel3, result.getClearSelectors().get( 1 ));
		
	}
}
