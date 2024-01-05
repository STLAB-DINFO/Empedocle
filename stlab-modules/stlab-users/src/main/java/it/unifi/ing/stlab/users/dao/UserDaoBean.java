package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class UserDaoBean implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

	
	@Override
	public User findByUsername(String userid) {
		if ( userid == null ) throw new IllegalArgumentException( "userid is null" );
		
		List<?> results = entityManager.createQuery( 
				"select u" +
				" from User u" +
				"  left join fetch u.roles " +
				" where u.userid = :puserid" )
			.setParameter( "puserid", userid )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (User)results.get( 0 );
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> findBySuggestion( String suggestion, int limit ) {
		Query q;

		q = entityManager.createQuery("select u from Staff s join s.user u " +
				" where CONCAT(u.name, ' ', u.surname) like :suggestion " +
				" order by u.surname, u.name");

		return q.setParameter("suggestion", '%' + suggestion + '%').getResultList();		
	}		
	
	// TODO testare
	@Override
	public Set<Qualification> listUserQualifications ( Long id ){
		Set<Qualification> result = new HashSet<Qualification>();
		Time now = new Time(new Date());
		
		for(Qualification q : entityManager.find(User.class, id).listQualifications()){
			if(q.isValidAt(now))
				result.add(q);
		}
		
		return result;
	}
	
}
