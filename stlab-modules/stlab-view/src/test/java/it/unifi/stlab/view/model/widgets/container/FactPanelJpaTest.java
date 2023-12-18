package it.unifi.stlab.view.model.widgets.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;

import org.junit.Test;

public class FactPanelJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		FactPanel panel = ViewerFactory.createFactPanel();
		
		panel.setName( "Panel Prova" );
		panel.setDescription( "Description Prova" );
		
		entityManager.persist( panel );
		
		uuid = panel.getUuid();
	}
	
	@Test
	public void testFactPanel(){
		FactPanel panel = 
				(FactPanel)entityManager.createQuery( " select p " +
																	" from FactPanel p " +
																	" where p.uuid = :uuid " )
													.setParameter("uuid", uuid)
													.getSingleResult();
		
		assertNotNull( panel );
		
		assertEquals( "Panel Prova", panel.getName() );
		assertEquals( "Description Prova", panel.getDescription() );
		
	}
	
}
