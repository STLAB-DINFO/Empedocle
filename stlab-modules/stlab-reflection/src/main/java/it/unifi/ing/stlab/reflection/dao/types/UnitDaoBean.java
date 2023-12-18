package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.types.Unit;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute
public class UnitDaoBean implements UnitDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Unit findByUuid(String uuid) {
		if ( uuid == null || uuid.trim().isEmpty() ) 
			return null;
		
		List<?> results = entityManager.createQuery(
				"select u " +
				" from Unit u " +
				" where u.uuid = :uuid")
			.setParameter("uuid", uuid)
			.setMaxResults( 1 )
			.getResultList();
	
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (Unit)results.get( 0 );
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Unit> findAll() {
		return (List<Unit>)entityManager.createQuery(
				"select u " +
				" from Unit u " +
				" order by u.name" )
			.getResultList();		
	}
}

