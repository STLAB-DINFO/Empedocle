package it.unifi.ing.stlab.reflection.dao.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class TypeDaoTest extends PersistenceTest {

	protected TypeDao typeDao;
	protected String uuid;
	protected Long typeId1;
	protected Long typeId2;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		typeDao = new TypeDaoBean();
		
		FieldUtils.assignField(typeDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		Type t1 = TypeFactory.createTextualType();
		Type t2 = TypeFactory.createTextualType();
		Type t3 = TypeFactory.createTextualType();
		
		uuid = t2.getUuid();
		
		entityManager.persist( t1 );
		entityManager.persist( t2 );
		entityManager.persist( t3 );

		typeId1 = t1.getId();
		typeId2 = t2.getId();
	}
	
	
	@Test
	public void testFindByUuid(){
		Type result = typeDao.findByUuid(uuid);
		
		assertEquals(uuid, result.getUuid());
		
	}
	
	@Test
	public void testFindByUuidFail1(){
		assertNull(typeDao.findByUuid(null));
		
	}
	
	@Test
	public void testFindByUuidFail2(){
		assertNull(typeDao.findByUuid("  "));
		
	}
	
	@Test
	public void testFindByUuidNotFound(){
		assertNull(typeDao.findByUuid("asd"));
		
	}
	
	@Test
	public void testDelete() {
		typeDao.delete( typeId2 );
		entityManager.getTransaction().commit();
		
		assertNull( typeDao.findById( typeId2 ));
	}
}
