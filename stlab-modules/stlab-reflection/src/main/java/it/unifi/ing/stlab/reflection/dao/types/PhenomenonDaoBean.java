package it.unifi.ing.stlab.reflection.dao.types;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.users.model.time.Time;

@Stateless
public class PhenomenonDaoBean implements PhenomenonDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Phenomenon> findByFact(QualitativeFact ql, Date date){
		if ( ql == null || ql.getType() == null ) return null;
		
		return findByType( ClassHelper.cast( ql.getType(), QualitativeType.class ), date );
	}

	@Override
	public List<Phenomenon> findByType(QualitativeType qlt, Date date){
		if ( qlt == null ) return null;
		
		if( ClassHelper.instanceOf( qlt, EnumeratedType.class )) {
			return findByEnumeratedType( ClassHelper.cast( qlt, EnumeratedType.class ), date);
		}

		if( ClassHelper.instanceOf( qlt, QueriedType.class )) {
			return findByQueriedType( ClassHelper.cast( qlt, QueriedType.class ), date);
		}
		
		return null;
	}
	
	@Override
	//FIXME riscrivere un po' meglio
	public List<Phenomenon> findBySuggestion(String suggestion, QualitativeType qlt, Date date, int limit) {
		StringBuffer query = new StringBuffer();
		
		if ( ClassHelper.instanceOf( qlt, QueriedType.class ) ) {
			query.append( ClassHelper.cast( qlt, QueriedType.class ).getQueryDef() );
			if ( query.toString().toLowerCase().lastIndexOf( "where" ) == -1 )
				query.append( " where " );
			else
				query.append( " and " );
			query.append( " p.name like :suggestion" );
		} else if ( ClassHelper.instanceOf( qlt, EnumeratedType.class ) ) {
			query.append( " select p from Type t join t.phenomena p " );
			query.append( " where t.uuid = :uuid " );
			query.append( " and p.name like :suggestion " );
		}
		
		TypedQuery<Phenomenon> q = entityManager.createQuery( query.toString(), Phenomenon.class );
		q.setParameter( "suggestion", "%" + suggestion + "%" );
		
		if ( limit > 0 ) {
			q.setMaxResults( limit );
		}
		
		if ( ClassHelper.instanceOf( qlt, EnumeratedType.class ) )
			q.setParameter( "uuid", qlt.getUuid() );
		
		List<Phenomenon> result = q.getResultList();
		Iterator<Phenomenon> i = result.iterator();
		while ( i.hasNext() ) {
			Phenomenon p = i.next();
			if ( !( p.isValidAt( new Time( date ) ) ) ) {
				i.remove();
			}
		}
		
		return result;
	}
	
	private List<Phenomenon> findByEnumeratedType(EnumeratedType enumType, Date date){
		List<Phenomenon> result = new LinkedList<Phenomenon>();
		for(Phenomenon p : enumType.listPhenomena()){
			if(p.isValidAt(new Time(date))){
				result.add(p);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private List<Phenomenon> findByQueriedType(QueriedType queriedType, Date date){
		List<Phenomenon> results = new LinkedList<Phenomenon>();
		for(Phenomenon p : (List<Phenomenon>)entityManager.createQuery(queriedType.getQueryDef()).getResultList()){
			if(p.isValidAt(new Time(date))){
				results.add(p);
			}
		}
		return results;
		
	}
	
	@Override
	public Phenomenon findByName(QualitativeFact ql, String name, Date date) {
		List<Phenomenon> phenomena = findByFact( ql, new Date() );
		for( Phenomenon p : phenomena ) {
			if( p.getName().equals( name ) )
				return p;
		}
		
		return null;
	}

	@Override
	public Phenomenon findByUuid(String uuid) {
		if(uuid == null || uuid.trim().isEmpty())
			return null;
		
		List<?> results = entityManager.createQuery( 
				"select p " +
				" from Phenomenon p " +
				"where p.uuid = :uuid")
			.setParameter("uuid", uuid)
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (Phenomenon)results.get( 0 );
	}

}
