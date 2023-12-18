package it.unifi.ing.stlab.view.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.visitor.ResolveLazyLoadVisitor;

@Stateless
public class ViewerDaoBean implements ViewerDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public int count( QueryBuilder builder ) {
		return ( (Long) builder.getCountQuery( entityManager ).getSingleResult() ).intValue();
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Viewer> find( QueryBuilder builder, int offset, int limit ) {
		Query query = builder.getListQuery( entityManager ).setFirstResult( offset );
		
		if( limit > 0 ) {
			query.setMaxResults( limit );
		} 
		
		return (List<Viewer>) query.getResultList();
	}

	@Override
	public Viewer findById( Long id ) {
		return entityManager.find( Viewer.class, id );
	}

	@Override
	public Viewer fetchById( Long id ) {
		if ( id == null )
			throw new IllegalArgumentException( "parametro null" );

		// Fetch selectors
		entityManager.createQuery( 
				"select ts " 
					+ " from TypeSelector ts, Viewer v "
					+ " join v.ancestors aa " 
					+ "  join v.children c "
					+ " where aa.id = :id and ts.root = c.selector " )
			.setParameter( "id", id )
			.getResultList();

		entityManager.createQuery( 
				"select v " 
					+ " from Viewer v " 
					+ "  join v.ancestors aa "
					+ "  left join fetch v.type " 
					+ "  left join fetch v.children "
					+ "  where aa.id = :id" )
			.setParameter( "id", id )
			.getResultList();

		Viewer v = entityManager.find( Viewer.class, id );

		if ( v != null )
			v.accept( new ResolveLazyLoadVisitor() );

		return v;
	}

	@Override
	public Viewer findByUuid( String uuid ) {
		if ( uuid == null || uuid.trim().isEmpty() )
			return null;

		List<Viewer> results = entityManager.createQuery( 
				"select v " 
					+ " from Viewer v " 
					+ " where v.uuid = :uuid", Viewer.class )
				.setParameter( "uuid", uuid )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public Viewer findByName( String name ) {
		if ( name == null || name.trim().isEmpty() )
			return null;

		List<Viewer> results = entityManager.createQuery( 
				"select v " 
					+ " from Viewer v " 
					+ " where v.name = :name", Viewer.class )
				.setParameter( "name", name )
				.setMaxResults( 1 )
				.getResultList();

		if ( results.size() == 0 ) {
			return null;
		}

		return results.get( 0 );
	}

	@Override
	public List<Viewer> findBySuggestion( String suggestion, int limit ) {
		TypedQuery<Viewer> query = entityManager.createQuery( 
				"select v from Viewer v "
						+ " where v.name like :suggestion " 
						+ " order by v.name", Viewer.class );
		
		query.setParameter( "suggestion", "%" + suggestion + "%" );
		
		if( limit > 0 ) {
			query.setMaxResults( limit );
		} 
		
		return query.getResultList();
	}

	@Override
	public void save( Viewer v ) {
		if ( v == null )
			throw new IllegalArgumentException( "parametro null" );

		entityManager.persist( v );
	}
	
	@Override
	public void delete( Long id ) {
		Viewer viewer = findById( id );
		
		if ( viewer == null ) return;
		
		viewer.delete();
		
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}

	@Override
	public boolean checkForeignKeyRestrictions( Long id ) {
		Boolean checkViewerUse = !entityManager.createQuery( 
				"select v from Viewer v, ViewerUse vu " 
					+ " where vu.viewer = v " 
					+ " and v.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();
		
		Boolean checkViewerLink = !entityManager.createQuery( 
				"select v from Viewer v, ViewerLink vl " 
					+ " where vl.target = v " 
					+ " and v.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		return checkViewerUse || checkViewerLink;
	}

}
