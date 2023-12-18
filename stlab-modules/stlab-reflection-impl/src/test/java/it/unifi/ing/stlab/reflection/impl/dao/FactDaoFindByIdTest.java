package it.unifi.ing.stlab.reflection.impl.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;

//XXX check
@Ignore
public class FactDaoFindByIdTest extends JpaTest {

	protected FactDao factDao;
	 
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );
		
		entityManager.clear();
	}
	

	@Test
	public void testFindById() {
		assertNotNull( factDao.findById( new Long( 1 ) ));
	}
	
	@Test
	public void testFetchById() {
		Fact fact = factDao.fetchById( new Long( 1 ) );
		inspectFact( fact );
	}
	
	private void inspectFact( Fact fact ) {
		InspectFactVisitor inspectFact = new InspectFactVisitor();
		fact.accept( inspectFact );
	}
	
}




