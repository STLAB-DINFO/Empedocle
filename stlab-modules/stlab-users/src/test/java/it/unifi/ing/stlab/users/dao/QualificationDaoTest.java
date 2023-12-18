package it.unifi.ing.stlab.users.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.model.Qualification;

import org.junit.Test;

public class QualificationDaoTest extends PersistenceTest {

	protected QualificationDao qualificationDao;
	protected String uuid;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		qualificationDao = new QualificationDaoBean();
		
		FieldUtils.assignField(qualificationDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		Qualification q1 = QualificationFactory.createQualification();
		Qualification q2 = QualificationFactory.createQualification();
		Qualification q3 = QualificationFactory.createQualification();
		
		uuid = q2.getUuid();
		
		entityManager.persist(q1);
		entityManager.persist(q2);
		entityManager.persist(q3);

	}
	
	
	@Test
	public void testFindByUuid(){
		Qualification result = qualificationDao.findByUuid(uuid);
		
		assertEquals(uuid, result.getUuid());
		
	}
	
	@Test
	public void testFindByUuidFail1(){
		assertNull(qualificationDao.findByUuid(null));
		
	}
	
	@Test
	public void testFindByUuidFail2(){
		assertNull(qualificationDao.findByUuid("  "));
		
	}
	
	@Test
	public void testFindByUuidNotFound(){
		assertNull(qualificationDao.findByUuid("asd"));
		
	}
	
}
