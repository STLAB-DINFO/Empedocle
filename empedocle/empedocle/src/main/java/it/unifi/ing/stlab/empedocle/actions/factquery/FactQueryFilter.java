package it.unifi.ing.stlab.empedocle.actions.factquery;

import it.unifi.ing.stlab.factquery.dao.FactQueryQueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named("queryFilter")
@SessionScoped
public class FactQueryFilter extends FilterBean implements FactQueryQueryBuilder { 

	private static final long serialVersionUID = 1L;

	public FactQueryFilter() {
		setPageSize( 10 );
	}
	
	
	@PostConstruct
	public void init() {
		initFilterDefs();
		
		initDefaultFilters();
		
		addSort( "Nome", "f.name asc", "f.name desc" );
		
		toggle( "Nome" );
	}
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( f ) from FactQuery f" );
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
		
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select f from FactQuery f" );
		
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}

	private void initFilterDefs(){
		addFilterDef( "Nome", FilterType.TEXT, "f.name like :fname", "fname" );
		addSort( "Nome", "f.name asc", "f.name desc" );
		toggle( "Nome" );
	}
	
	private void writeFilters( StringBuffer buffer ) {
		//se si inserisce il filtro per un altro campo, rivedere questo metodo
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed() ) {
				buffer.append( " where " )
					  .append( filter.getDefinition().getExpression() );
			}
		}
	}

}
