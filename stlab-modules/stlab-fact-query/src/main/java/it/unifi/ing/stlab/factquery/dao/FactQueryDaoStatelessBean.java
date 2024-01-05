package it.unifi.ing.stlab.factquery.dao;

import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.factquery.model.ResolveLazyLoadVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@TransactionAttribute
public class FactQueryDaoStatelessBean implements FactQueryDao {

	@PersistenceContext
	private EntityManager entityManager;
	
//	@EJB
//	private FactDao factDao;
	
	@Override
	public int count(FactQueryQueryBuilder builder) {
		return ( (Long) builder.getCountQuery( entityManager )
						.getSingleResult() )
						.intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FactQuery> find(FactQueryQueryBuilder builder, int offset, int limit) {
		Query query = builder
				.getListQuery( entityManager )
				.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<FactQuery>) query.getResultList();
	}

	@Override
	public FactQuery findById(Long id) {
		FactQuery result = entityManager.find( FactQuery.class, id );
		entityManager.createQuery( 
				"select t " +
				" from Expression t " +
				"  join t.ancestors aa " + 
				"  left join fetch t.children " +
				"  left join fetch t.parents " +
				" where aa.id = :id" ).setParameter( "id", result.getExpression().getId() ).getResultList();
		
		result.getExpression().accept( new ResolveLazyLoadVisitor() );
		
		return result;
		
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<Fact> findFacts(Long id, FactQueryConstructor queryConstructor) {
		FactQuery result = findById( id );
		
		if( result == null )
			return null;
		
		List<Fact> factRoots = ( List<Fact> )queryConstructor.buildQuery( entityManager, result )
													.getResultList();

//		FIXME da reimplementare il fetch
//		if( result.getFetch() ) {
//			for( Fact fact : factRoots ) {
//				factDao.fetchById( ClassHelper.cast( fact, FactImpl.class ).getId() );
//				
//			}
//		}
		
		return factRoots;
		
	}

}
