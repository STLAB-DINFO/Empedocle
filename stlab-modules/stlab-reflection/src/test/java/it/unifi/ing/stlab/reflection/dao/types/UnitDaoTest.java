package it.unifi.ing.stlab.reflection.dao.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class UnitDaoTest extends PersistenceTest {

	protected UnitDao unitDao;
	protected String uuid;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		unitDao = new UnitDaoBean();
		
		FieldUtils.assignField(unitDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		Unit u1 = UnitFactory.createUnit();
		Unit u2 = UnitFactory.createUnit();
		Unit u3 = UnitFactory.createUnit();
		
		uuid = u2.getUuid();
		
		entityManager.persist(u1);
		entityManager.persist(u2);
		entityManager.persist(u3);

	}
	
	
	@Test
	public void testFindByUuid(){
		Unit result = unitDao.findByUuid(uuid);
		
		assertEquals(uuid, result.getUuid());
		
	}
	
	@Test
	public void testFindByUuidFail1(){
		assertNull(unitDao.findByUuid(null));
		
	}
	
	@Test
	public void testFindByUuidFail2(){
		assertNull(unitDao.findByUuid("  "));
		
	}
	
	@Test
	public void testFindByUuidNotFound(){
		assertNull(unitDao.findByUuid("asd"));
	}

	@Test
	public void testFindAll() {
		assertEquals( 3, unitDao.findAll().size() );
	}

}
