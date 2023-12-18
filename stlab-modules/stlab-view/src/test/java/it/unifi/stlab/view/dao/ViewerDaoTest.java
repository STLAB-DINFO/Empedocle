package it.unifi.stlab.view.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.output.Label;

import java.util.UUID;

import org.junit.Test;

public class ViewerDaoTest extends PersistenceTest {

	protected ViewerDao viewerDao;
	protected String uuid;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		viewerDao = new ViewerDaoBean();
		
		FieldUtils.assignField(viewerDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		uuid = UUID.randomUUID().toString();
		Viewer v1 = new Label(uuid);
		v1.setName("Prova");
		v1.init();
		Viewer v2 = new Label(UUID.randomUUID().toString());
		v2.setName("Prova 2");
		v2.init();
		Grid g = new Grid(UUID.randomUUID().toString());
		g.init();
		SubViewer sb = new SubViewer(UUID.randomUUID().toString());
		sb.assignSource(g);
		sb.assignTarget(v1);
		
		entityManager.persist(g);
		entityManager.persist(v2);
	}
	
	
	@Test
	public void testFindByUuid(){
		Viewer result = viewerDao.findByUuid(uuid);
		
		assertEquals(uuid, result.getUuid());
		
	}
	
	@Test
	public void testFindByUuidFail1(){
		assertNull(viewerDao.findByUuid(null));
		
	}
	
	@Test
	public void testFindByUuidFail2(){
		assertNull(viewerDao.findByUuid("  "));
		
	}
	
	@Test
	public void testFindByUuidNotFound(){
		assertNull(viewerDao.findByUuid("asd"));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSaveFail(){
		viewerDao.save(null);
		
	}
	
}
