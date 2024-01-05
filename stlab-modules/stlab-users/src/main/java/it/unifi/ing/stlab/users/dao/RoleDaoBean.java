package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class RoleDaoBean implements RoleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Role findById( Long id ) {
		List<Role> results = entityManager.createQuery( 
				"select r " 
					+ " from Role r" 
					+ " where r.id = :id", Role.class )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.isEmpty() ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public Role findByName( String name ) {
		List<Role> results = entityManager.createQuery( 
				"select r " 
					+ " from Role r" 
					+ " where r.name = :name", Role.class )
				.setParameter( "name", name )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.isEmpty() ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public Role findByUuid( String uuid ) {
		if ( uuid == null || uuid.trim().isEmpty() )
			throw new IllegalArgumentException( "Parametro null" );

		List<Role> results = entityManager.createQuery( 
				"select r " 
					+ " from Role r " 
					+ " where r.uuid = :uuid", Role.class )
				.setParameter( "uuid", uuid )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.isEmpty() ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public List<Role> getAll() {
		return entityManager.createQuery( 
				"from Role r", Role.class )
				.getResultList();
	}

	@Override
	public List<Role> findBySuggestion( String suggestion, int limit ) {
		TypedQuery<Role> query = entityManager
				.createQuery( 
				" select r from Role r " 
					+ " where r.name like :suggestion", Role.class );
		
		query.setParameter( "suggestion", "%" + suggestion + "%" );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}
}
