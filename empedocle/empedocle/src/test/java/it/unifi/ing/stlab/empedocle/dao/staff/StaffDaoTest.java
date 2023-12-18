package it.unifi.ing.stlab.empedocle.dao.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;

//XXX check
@Ignore
public class StaffDaoTest extends JpaTest {

	private StaffDao staffDao;
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		staffDao = new StaffDaoBean();
		FieldUtils.assignField(staffDao, "entityManager", entityManager);
	}
	
	@Test
	public void testFindByUser() {
		Long id = new Long(1l);
		Staff s = staffDao.findByUserId(id);
		
		assertNotNull(s);
		assertEquals(id, s.getUser().getId());
	}
	
	@Test
	public void testFetchById() {
		Staff s = staffDao.fetchById(2L);
		
		assertNotNull(s);
		
		assertNotNull(s.getUser().listRoles());
		
		assertEquals("utente1", s.getUser().getUserid());
		assertEquals("pass", s.getUser().getPassword());
		
		assertEquals(1, s.getUser().listRoles().size());
		assertEquals("user", s.getUser().listRoles().iterator().next().getName());
		
	}
	
}
