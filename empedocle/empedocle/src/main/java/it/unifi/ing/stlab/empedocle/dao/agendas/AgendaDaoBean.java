package it.unifi.ing.stlab.empedocle.dao.agendas;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Agenda;

@Stateless
public class AgendaDaoBean implements AgendaDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int count( QueryBuilder builder ) {
		return ( (Long) builder.getCountQuery( entityManager ).getSingleResult() ).intValue();
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Agenda> find( QueryBuilder builder, int offset, int limit ) {
		Query query = builder.getListQuery( entityManager ).setFirstResult( offset );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<Agenda>) query.getResultList();
	}

	@Override
	public List<Agenda> findAll() {
		return entityManager.createQuery( 
				"select a " 
					+ " from Agenda a" 
					+ " order by a.code ", Agenda.class )
				.getResultList();
	}

	@Override
	public List<Agenda> findByPrefix( String prefix, String username, int limit ) {
		TypedQuery<Agenda> query = entityManager.createQuery( 
					" select distinct( a ) from Staff s join s.agendas a "
						+ " where s.user.userid = :userId " 
						+ " and (a.code like :codePrefix "
							+ " or a.description like :descriptionPrefix)", Agenda.class );
		
		query.setParameter( "codePrefix", prefix + '%' )
			.setParameter( "descriptionPrefix", '%' + prefix + '%' )
			.setParameter( "userId", username );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public Agenda findById( Long id ) {
		List<Agenda> results = entityManager.createQuery( 
					"select a " 
						+ " from Agenda a " 
						+ " where a.id = :id", Agenda.class )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public Agenda findByUuid( String uuid ) {
		if ( uuid == null || uuid.trim().isEmpty() )
			throw new IllegalArgumentException( "Parametro uuid null" );

		List<Agenda> results = entityManager.createQuery( 
					"select a " 
						+ " from Agenda a " 
						+ " where a.uuid = :uuid", Agenda.class )
				.setParameter( "uuid", uuid )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public void save( Agenda agenda ) {
		entityManager.persist( agenda );
	}

	@Override
	public void update( Agenda agenda ) {
		entityManager.merge( agenda );
	}

	@Override
	public void delete( Long id ) {
		entityManager.remove( findById( id ) );
	}

	// agende possono essere vincolate da Staff e Prestazioni
	@Override
	public Boolean checkForeignKeyRestrictions( Long id ) {
		Boolean checkStaff = !entityManager.createQuery( 
					"select s.id from Staff s join s.agendas a " 
						+ " where a.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		Boolean checkServices = !entityManager.createQuery( 
					"select s.id from Service s " 
						+ " where s.agenda.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		return checkStaff || checkServices;
	}

	// TODO test
	@Override
	public Agenda findByCode( String code ) {
		if ( code == null )
			throw new IllegalArgumentException( "code is null" );

		List<Agenda> results = entityManager.createQuery( 
				"select a " 
					+ " from Agenda a " 
					+ " where a.code = :code", Agenda.class )
				.setParameter( "code", code )
				.setMaxResults( 2 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}
		if ( results.size() == 2 ) {
			throw new RuntimeException( "Error: More than one agenda with code = " + code );
		}

		return results.get( 0 );
	}

	@Override
	public List<Agenda> findBySuggestion( String suggestion, int limit ) {

		TypedQuery<Agenda> query = entityManager.createQuery( 
				" select a from Agenda a "
					+ " where CONCAT( a.code, ' ', a.description ) like :suggestion", Agenda.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%' );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public List<Agenda> findBySuggestion( String suggestion, String username, int limit ) {

		TypedQuery<Agenda> query = entityManager.createQuery( 
				" select a from Staff s join s.agendas a "
					+ " where s.user.userid = :userId "
					+ " and CONCAT( a.code, ' ', a.description ) like :suggestion"
					+ " order by a.code", Agenda.class );
		
		query.setParameter( "userId", username )
			.setParameter( "suggestion", '%' + suggestion + '%' );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public Boolean checkUnusedAgendas( List<Agenda> exclude ) {
		return entityManager.createQuery( 
				" select count( a ) from Agenda a "
					+ " where a.examinationType is null " 
					+ " and a not in :excluded ", Long.class )
				.setParameter( "excluded", exclude )
				.getSingleResult()
				.intValue() > 0;
	}

	@Override
	public Boolean checkUnusedAgendas() {
		return entityManager.createQuery( 
				" select count( a ) from Agenda a " 
					+ " where a.examinationType is null ", Long.class )
				.getSingleResult()
				.intValue() > 0;
	}

	@Override
	public List<Agenda> findUnusedAgendasBySuggestion( String suggestion, int limit ) {
		TypedQuery<Agenda> query = entityManager.createQuery( 
				" select a from Agenda a " 
						+ " where a.examinationType is null "
						+ " and CONCAT( a.code, ' ', a.description ) like :suggestion" 
						+ " order by a.code", Agenda.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%' );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public List<Agenda> findUnusedAgendasBySuggestion( String suggestion, int limit,
			List<Agenda> exclude ) {
		TypedQuery<Agenda> query = entityManager.createQuery( 
			" select a from Agenda a " 
				+ " where a.examinationType is null "
				+ " and a not in :excluded "
				+ " and CONCAT( a.code, ' ', a.description ) like :suggestion" 
				+ " order by a.code", Agenda.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%' )
			.setParameter( "excluded", exclude );

		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	@Override
	public List<Agenda> findFavoriteAgendasByUsername( String username ) {
		return entityManager.createQuery( 
				" select a from Staff s join s.favoriteAgendas a "
					+ " where s.user.userid = :userId " 
					+ " order by a.code", Agenda.class )
				.setParameter( "userId", username )
				.getResultList();
	}

	@Override
	public List<Agenda> findByExaminationTypeId( Long examTypeId ) {
		return entityManager.createQuery( 
				" select a from Agenda a " 
					+ " where a.examinationType.id = :id" 
					+ " order by a.code", Agenda.class )
				.setParameter( "id", examTypeId )
				.getResultList();
	}
}
