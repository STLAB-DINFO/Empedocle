package it.unifi.ing.stlab.test;


import org.junit.Before;
import org.junit.BeforeClass;

public abstract class PersistenceTest extends JpaTest {

	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test" );
	}
	 
	@Before 
	public void setUp() throws Exception {
		super.setUp();

		entityManager.getTransaction().begin();
        clearData();
        entityManager.getTransaction().commit();
        
		entityManager.getTransaction().begin();
        insertData();
        entityManager.getTransaction().commit();
        entityManager.clear();
		
        entityManager.getTransaction().begin();
	}
	
	protected void clearData() throws Exception {
		entityManager.createNativeQuery( "TRUNCATE SCHEMA public AND COMMIT" ).executeUpdate();
	}

    protected void insertData() throws Exception {
    }
}
