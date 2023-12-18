package it.unifi.ing.stlab.test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

public abstract class JpaTest {

	protected static EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	
	public static void initEntityManagerFactory( String persistenceUnit ) {
		entityManagerFactory = Persistence.createEntityManagerFactory( persistenceUnit );
	}
	 
	@Before
	public void setUp() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterClass
	public static void tearDownClass() {
		entityManagerFactory.close();
	}
	
	@After
	public void tearDown() {
		if ( entityManager.getTransaction().isActive() ) {
			entityManager.getTransaction().rollback();
		}
		entityManager.close();
	}
	
}
