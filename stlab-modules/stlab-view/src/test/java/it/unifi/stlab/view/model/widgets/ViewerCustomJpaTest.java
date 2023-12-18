package it.unifi.stlab.view.model.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;

import java.util.UUID;

import org.junit.Test;

public class ViewerCustomJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		
		ViewerCustom custom = new ViewerCustom( uuid );
		
		custom.setName( "nome" );
		custom.setXhtmlFileName( "nomeViewer" );

		entityManager.persist( custom );
	}
	
	@Test
	public void testViewerCustom() {
		ViewerCustom result = entityManager
									.createQuery(" select v " +
													" from ViewerCustom v " +
													" where v.uuid = :uuid ", 
													ViewerCustom.class)
									.setParameter("uuid", uuid)
									.getSingleResult();
		
		assertNotNull( result );
		assertEquals( "nome", result.getName() );
		assertEquals( "nomeViewer", result.getXhtmlFileName() );
		assertEquals( "../component/nomeViewer.xhtml", result.getXhtml() );
	}
}
