package it.unifi.ing.stlab.empedocle.dao.staff;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Staff;

@Stateless
@TransactionAttribute
public class StaffDaoBean implements StaffDao {

	@PersistenceContext()
	private EntityManager entityManager;

	@Override
	public int count( QueryBuilder builder ) {
		return ( (Long) builder.getCountQuery( entityManager ).getSingleResult() ).intValue();
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Staff> find( QueryBuilder builder, int offset, int limit ) {
		Query query = builder.getListQuery( entityManager ).setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public Staff findById( Long id ) {
		List<Staff> results = entityManager.createQuery( 
				"select s from Staff s" 
					+ " where s.id = :id", Staff.class )
				.setParameter( "id", id )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	public Staff findByUserId( Long id ) {
		List<Staff> results = entityManager.createQuery( 
				"select s from Staff s" 
					+ " where s.user.id = :id", Staff.class )
				.setParameter( "id", id )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return (Staff) results.get( 0 );
	}

	public Staff fetchById( Long id ) {
		List<Staff> result = entityManager.createQuery( 
				"select distinct(s) from Staff s" 
						+ " left join fetch s.agendas a"
						+ " left join fetch s.user u" + " left join fetch u.roles r"
						+ " left join fetch u.qualifications q" + " where s.id = :id", Staff.class )
				.setParameter( "id", id )
				.getResultList();

		if ( result.size() == 1 ) {
			return result.get( 0 );
		} else if ( result.size() == 0 ) {
			return null;
		} else {
			throw new RuntimeException( "Più di un risultato da StaffDAO per id = " + id );
		}
	}

	@Override
	public Staff fetchByUsername( String username ) {
		List<Staff> result = entityManager.createQuery( 
				"select distinct(s) from Staff s" 
						+ " left join fetch s.agendas a"
						+ " left join fetch s.favoriteAgendas fa"
						+ " left join fetch s.user u" 
						+ " left join fetch u.roles r"
						+ " left join fetch u.qualifications q" 
						+ " where s.user.userid = :userId", Staff.class )
				.setParameter( "userId", username )
				.getResultList();

		if ( result.size() == 1 ) {
			return result.get( 0 );
		} else if ( result.size() == 0 ) {
			return null;
		} else {
			throw new RuntimeException( "Più di un risultato da StaffDAO per username = " + username );
		}
	}

	public void save( Staff target ) {
		entityManager.persist( target );
		entityManager.flush();
	}

	public void update( Staff target ) {

		entityManager.merge( target );
	}

	public void delete( Long id ) {
		entityManager.remove( findById( id ) );
	}

	@Override
	public Boolean checkForeignKeyRestrictions( Staff s ) {
		Boolean checkExaminations = !entityManager.createQuery( 
				"select e from Examination e " 
					+ " where e.author = :user" )
				.setParameter( "user", s.getUser() )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		Boolean checkFactActions = !entityManager.createQuery( 
				"select fa from FactAction fa " 
					+ " where fa.author = :user" )
				.setParameter( "user", s.getUser() )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		Boolean patientActions = !entityManager.createQuery( 
				"select pa from PatientAction pa" 
					+ " where pa.author = :user" )
				.setParameter( "user", s.getUser() )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();		

		return checkExaminations || checkFactActions || patientActions;
	}
}
