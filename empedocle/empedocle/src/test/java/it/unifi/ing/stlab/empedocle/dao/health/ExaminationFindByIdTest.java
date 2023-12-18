package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;

//XXX check
@Ignore
public class ExaminationFindByIdTest extends JpaTest {

	protected ExaminationDao examinationDao;
	 
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		FactDao factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );

		ViewerDao viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );
		
		TypeDao typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );
		
		examinationDao = new ExaminationDaoBean();
		FieldUtils.assignField( examinationDao, "entityManager", entityManager );
		FieldUtils.assignField( examinationDao, "factDao", factDao );
		FieldUtils.assignField( examinationDao, "viewerDao", viewerDao );
		FieldUtils.assignField( examinationDao, "typeDao", typeDao );
		
		entityManager.clear();
	}
	

	@Test
	public void testFindById() {
		assertNotNull( examinationDao.findById( new Long( 181896 ) ));
	}
	
	@Test
	public void testFetchById() {
		ExaminationDetails examinationDetails = examinationDao.fetchById( new Long( 181896 ), new Long( 1 ), ExaminationTypeContext.VIEW );
	
		// TODO inspect ExaminationDetails
	}
	
	
}




