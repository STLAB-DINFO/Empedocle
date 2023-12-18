package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.model.facts.values.FactValue;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.type.ResolveLazyLoadVisitor;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


@Stateless
@TransactionAttribute
public class TypeDaoBean implements TypeDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(TypeQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Type> find(TypeQueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<Type>) query.getResultList();
	}
	
	@Override
	public Type findByExaminationType( Long examinationTypeId ) {
		List<Type> result = entityManager.createQuery(
				"select t from Type t, ExaminationType et " 
					+ " where et.type = t " 
					+ " and et.id = :id", Type.class )
				.setMaxResults( 1 )
				.setParameter( "id", examinationTypeId )
				.getResultList();
		
		if ( result.size() == 0 ) {
			return null;
		} else {
			return result.get( 0 );
		}
	}
	
	@Override
	public List<Type> findBySuggestion(String suggestion, int limit) {
		TypedQuery<Type> query = entityManager.createQuery(
				"from Type t " 
					+ " where t.name like :name" 
					+ " order by t.name", Type.class );
		
		query.setParameter( "name", "%" + suggestion.trim() + "%" );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}
	
	@Override
	public Type findByName(String name) {
		if ( name == null || name.trim().isEmpty() )
			return null;
		
		List<?> results = entityManager.createQuery(
				"select t " +
				" from Type t " +
				" where t.name = :name")
			.setParameter("name", name)
			.setMaxResults( 1 )
			.getResultList();
	
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (Type)results.get( 0 );		
	}	
		

	@Override
	public Type findById(Long id) {
		return entityManager.find( Type.class, id );
	}
	
	@Override
	public Type fetchById(Long id) {
		entityManager.createQuery( 
			" select t " 
				+ " from Type t " 
				+ " join t.ancestors aa " 
				+ " left join fetch t.children " 
				+ " left join fetch t.parents " 
				+ " where aa.id = :id" )
		.setParameter( "id", id )
		.getResultList();

		Type result = entityManager.find( Type.class, id );
		result.accept( new ResolveLazyLoadVisitor() );
		
		return result;
	}
	
	@Override
	public FactValue fetchFactValue(TypeLink tl) {
		List<FactValue> result = entityManager.createQuery(
				"select dv " 
					+ " from TypeLink tl " 
					+ " join tl.defaultValue dv " 
					+ " where tl.id = :id", FactValue.class )
				.setParameter("id", tl.getId())
				.setMaxResults( 1 )
				.getResultList();
		
		if ( result.size() > 0 ) {
			return result.get(0);
		}
		else {
			return null;
		}
		
	}
	
	@Override
	public void assignToLink(TypeLink link, Type type) {
//		fetchByIdWithFullHierarchy(link.getSource().getId());
		if(type != null) {
			link.assignTarget(fetchByIdWithFullHierarchy(type.getId()));
		}
		else {
			link.assignTarget(null);
		}
		
	}
	
	public Type fetchByIdWithFullHierarchy(Long id) {
		entityManager.createQuery( 
			" select t " 
				+ " from Type t " 
				+ " left join fetch t.ancestors aa " 
				+ " left join fetch t.descendents dd " 
				+ " left join fetch t.children " 
				+ " left join fetch t.parents " 
				+ " where t.id = :id" )
		.setParameter( "id", id )
		.getResultList();

		Type result = entityManager.find( Type.class, id );
		result.accept( new ResolveLazyLoadVisitor() );
		
		return result;
	}
	
	@Override
	public QuantitativeType fetchWithUnitUses(Long id) {
		List<QuantitativeType> result = entityManager.createQuery(
				"select distinct(q) from QuantitativeType q " 
					+ " left join fetch q.units uu " 
					+ " left join fetch uu.unit u " 
					+ " where q.id = :id", QuantitativeType.class )
				.setParameter("id", id)
				.getResultList();
		
		if ( result.size() == 1 ) {
			return result.get(0);
		}
		else if ( result.size() == 0 ) {
			return null;
		}
		else {
			throw new RuntimeException( "Error: More than one type returned" ); 
		}
				
	}

	@Override
	public Type findByUuid(String uuid) {
		if(uuid == null || uuid.trim().isEmpty())
			return null;
		
		List<Type> results = entityManager.createQuery( 
				"select t " 
					+ " from Type t " 
					+ " where t.uuid = :uuid", Type.class )
			.setParameter("uuid", uuid)
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return results.get( 0 );
	}

	@Override
	public void delete(Long id) {
		Type type = findById( id );
		
		if ( type == null ) return;
		
		type.delete();
		
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}
	
	@Override
	public void save(Type t) {
		if(t == null) 
			throw new IllegalArgumentException("parametro null");
		
		entityManager.persist( t );
	}

	@Override
	public boolean checkForeignKeyRestrictions( Long id ) {
		Boolean checkExaminationType = !entityManager.createQuery( 
			"select t from Type t, ExaminationType et " 
					+ " where et.type = t " 
					+ " and t.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		Boolean checkViewer = !entityManager.createQuery( 
				"select t from Type t, Viewer v " 
					+ " where v.type = t " 
					+ " and t.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();
		
		Boolean checkFact = !entityManager.createQuery( 
				"select t from Type t, FactImpl f " 
						+ " where f.type = t " 
						+ " and t.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();
		
		Boolean checkTypeLink = !entityManager.createQuery( 
				"select t from Type t, TypeLink tl " 
					+ " where tl.target = t " 
					+ " and t.id = :id" )
				.setParameter( "id", id )
				.setMaxResults( 1 )
				.getResultList()
				.isEmpty();

		return checkExaminationType || checkViewer || checkFact || checkTypeLink;
	}	
}
