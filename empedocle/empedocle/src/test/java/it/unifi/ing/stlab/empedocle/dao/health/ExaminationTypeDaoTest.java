package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeFilter;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.model.Viewer;

//XXX check
@Ignore
public class ExaminationTypeDaoTest extends JpaTest{

	protected ExaminationTypeDao examinationTypeDao;
	protected ExaminationTypeFilter examinationTypeFilter;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		examinationTypeDao = new ExaminationTypeDaoBean();
		FieldUtils.assignField(examinationTypeDao, "entityManager", entityManager);
		
		examinationTypeFilter = new ExaminationTypeFilter();
		
		entityManager.clear();
	}
	
	
	@Test
	public void testFindNoFilter(){
		List<ExaminationType> result = examinationTypeDao.find(examinationTypeFilter, 0, 50);
		
		assertEquals(1, result.size());
	}
	
	@Test
	public void testFindById(){
		ExaminationType result = examinationTypeDao.findById( new Long(1) );
		
		assertEquals("Cardiologia Dr. Fabio Mori - II rev", result.getName());	
	}
	
	@Test
	public void testFindByExaminationId() {
		ExaminationType type = examinationTypeDao.findByExaminationId( new Long(181896) );
		assertNotNull( type );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev", type.getName() );
	}
	
	@Test
	public void testFindAssociatedViewer() {
		List<Viewer> result = examinationTypeDao.findAssociatedViewer( new Long(1), new Long(1), ExaminationTypeContext.REPORT );
		assertNotNull( result );
		assertEquals( 2, result.size() );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev - ETICHETTA", result.get(0).getName() );
		assertEquals( "Cardiologia Dr. Fabio Mori - II rev - REPORT", result.get(1).getName() );
	}
	
}
