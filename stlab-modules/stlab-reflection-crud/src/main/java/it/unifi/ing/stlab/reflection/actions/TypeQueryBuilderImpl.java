package it.unifi.ing.stlab.reflection.actions;

import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.reflection.dao.types.TypeQueryBuilder;
import it.unifi.ing.stlab.reflection.model.types.Type;

import javax.persistence.EntityManager;
import javax.persistence.Query;

// TODO test TypeQueryBuilderImpl
public class TypeQueryBuilderImpl implements TypeQueryBuilder {

	private Class<? extends Type> typeClass;
	private TypeFilter typeFilter;
	
	public TypeQueryBuilderImpl(Class<? extends Type> typeClass, TypeFilter typeFilter) {
		super();
		this.typeClass = typeClass;
		this.typeFilter = typeFilter;
	}

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( t ) " )
			  .append( " from " ).append( typeClass.getCanonicalName() ).append( " t " )
			  .append( " where t.anonymous = false" );
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
		
	} 
	
	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select t " )
			  .append( " from " ).append( typeClass.getCanonicalName() ).append( " t " )
			  .append( " where t.anonymous = false" );
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}
	
	
	private void writeFilters( StringBuffer buffer ) {
		for ( Filter filter : typeFilter.getFilters() ) {
			if ( filter.isUsed() ) {
				buffer.append( " and " )
					  .append( filter.getDefinition().getExpression() );
			}
		}
	}

	private void resolveParameters( Query query ) {
		for ( Filter filter : typeFilter.getFilters() ) {
			if ( filter.isUsed() ) {
				filter.resolveParameter( query ); 
			}
		}
	}
	
	private String getSorting() {
		return typeFilter.getSorting();
	}
	
}
