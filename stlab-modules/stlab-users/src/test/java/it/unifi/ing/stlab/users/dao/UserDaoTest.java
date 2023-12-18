package it.unifi.ing.stlab.users.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.RoleFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Role;
import it.unifi.ing.stlab.users.model.User;

import org.junit.Test;

public class UserDaoTest extends PersistenceTest {

	protected UserDao userDao;
	protected Long id;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		userDao = new UserDaoBean();
		
		FieldUtils.assignField( userDao, "entityManager", entityManager );
	}
	
	@Override 
	protected void insertData() throws Exception {
		Role role = RoleFactory.createRole();
		role.setName( "admin" );
		entityManager.persist( role );
		
		User user = UserFactory.createUser();
		user.setUserid( "abc" );
		user.setName( "Mario" );
		user.setSurname( "Rossi" );
		user.addRole( role );

		entityManager.persist( user );
		id = user.getId();
	}
	
	
	@Test
	public void testFindById(){
		assertNotNull( userDao.findById( id ));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByIdFail(){
		userDao.findById( null );
	}
	
	
	@Test
	public void testFindByUserid(){
		assertNotNull( userDao.findByUsername( "abc" ));
	}
	
	
	@Test
	public void testFindByUseridNotFound(){
		assertNull( userDao.findByUsername( "efg" ));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByUseridFail(){
		assertNull( userDao.findByUsername( null ));
	}
}
