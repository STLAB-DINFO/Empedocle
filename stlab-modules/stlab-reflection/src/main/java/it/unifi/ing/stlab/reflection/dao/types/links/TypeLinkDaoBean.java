package it.unifi.ing.stlab.reflection.dao.types.links;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.type.ResolveLazyLoadVisitor;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute
public class TypeLinkDaoBean implements TypeLinkDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<TypeLink> findBySuggestion( String suggestion, int limit) {
		TypedQuery<TypeLink> query = entityManager.createQuery( 
				" select tl from TypeLink tl " 
					+ " where tl.name like :name", TypeLink.class );
		
		query.setParameter( "name", "%" + suggestion.trim() + "%" );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();	
	}
	
	@Override
	public TypeLink findById( Long id ) {
		return entityManager.find( TypeLink.class, id );
		
	}
	
	@Override
	public TypeLink findByUuid( String uuid ) {
		//FIXME fetch incorporato
		List<TypeLink> results = entityManager.createQuery( 
				" select tl from TypeLink tl join fetch tl.target" 
						+ " where tl.uuid = :uuid", TypeLink.class )
				.setParameter( "uuid", uuid )
				.setMaxResults( 1 )
				.getResultList();
		
		if( results.size() == 0 )
			return null;
		
		results.get( 0 ).getTarget().accept( new ResolveLazyLoadVisitor() );
		
		return results.get( 0 );
	}
	
	@Override
	public TypeLink findByName(String name) {
		List<TypeLink> results = entityManager.createQuery(
				"select distinct t from TypeLink t " 
						+ " left join fetch t.target " 
						+ " where t.name = :name", TypeLink.class )
				.setParameter( "name", name )
				.getResultList();
						
		if ( results == null || results.size() < 1 )
			throw new IllegalArgumentException( "Nessuna sotto tipo con nome " + name );

		if ( results.size() > 1 )
			throw new NonUniqueResultException( "Più di un sotto tipo con nome " + name );
		
		return results.get( 0 );
	}
	
	@Override
	public TypeLink findChildrenByName(Type type, String name) {
		List<TypeLink> results = entityManager.createQuery(
				"select distinct i from CompositeType c" 
						+ " left join c.children i " 
						+ " left join fetch i.target " 
						+ " where c.id = :type and i.name = :nameLink", TypeLink.class )
				.setParameter( "nameLink", name )
				.setParameter( "type", type.getId() )
				.getResultList();
						
		if ( results == null || results.size() < 1 )
			throw new IllegalArgumentException( "Nessuna sotto tipo con nome " + name );

		if ( results.size() > 1 )
			throw new NonUniqueResultException( "Più di un sotto tipo con nome " + name );
		
		return results.get( 0 );
	}
	
}
