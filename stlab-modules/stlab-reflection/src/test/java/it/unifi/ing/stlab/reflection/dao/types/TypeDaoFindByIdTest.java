package it.unifi.ing.stlab.reflection.dao.types;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;

//XXX check
@Ignore
public class TypeDaoFindByIdTest extends JpaTest {

	protected TypeDao typeDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		typeDao = new TypeDaoBean();
		FieldUtils.assignField(typeDao, "entityManager", entityManager );
		
		entityManager.clear();
	}
	

	@Test
	public void testFindById() {
		assertNotNull( typeDao.findById( new Long( 568 ) ));
	}

	@Test
	public void testFetchById() {
		Type type  = typeDao.fetchById( new Long( 568 ) );
		inspectType( type );
	}
	
	private void inspectType( Type type ) {
		InspectTypeVisitor inspectType = new InspectTypeVisitor();
		type.accept( inspectType );
	}
	
}

