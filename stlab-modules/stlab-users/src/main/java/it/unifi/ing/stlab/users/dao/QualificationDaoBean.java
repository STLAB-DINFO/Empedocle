package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class QualificationDaoBean implements QualificationDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Qualification findById(Long id) {
		return entityManager.find( Qualification.class, id );
	}
	
	@Override
	public Qualification findByUuid(String uuid) {
		if ( uuid == null || uuid.trim().isEmpty() ) 
			return null;
		
		List<Qualification> results =entityManager.createQuery(
				"select q " 
					+ " from Qualification q " 
					+ " where q.uuid = :uuid", Qualification.class )
			.setParameter("uuid", uuid)
			.setMaxResults( 1 )
			.getResultList();
	
		if ( results.size() == 0 ) {
			return null;
		}
		
		return results.get( 0 );
	}

	@Override
	public List<Qualification> findBySuggestion( String suggestion, int limit ) {
		TypedQuery<Qualification> query = entityManager.createQuery(
				" select q " 
					+ " from Qualification q " 
					+ " where q.name like :suggestion", Qualification.class );
		
		query.setParameter( "suggestion", "%" + suggestion + "%" );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}
	
	@Override
	public List<Qualification> getAll() {
		return entityManager.createQuery( 
				"from Qualification q", Qualification.class )
				.getResultList();
	}

}
