package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationQueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterDef;
import it.unifi.ing.stlab.filters.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ExaminationQueryBuilderImpl implements ExaminationQueryBuilder {

	private final ExaminationFilter examinationFilter;
	private Set<Filter> predefinedFilters;
	
	private Boolean includeAuthorInQuery;
	
	public ExaminationQueryBuilderImpl( ExaminationFilter examinationFilter ) {
		this.examinationFilter = examinationFilter;
		predefinedFilters = new HashSet<Filter>();
		
		includeAuthorInQuery = false;
	}
	
	
	protected Set<Filter> getPredefinedFilters() {
		return predefinedFilters;
	}
	protected void setPredefinedFilters(Set<Filter> predefinedFilters) {
		this.predefinedFilters = predefinedFilters;
	}
	protected void addPredefinedFilter( Filter filter ) {
		if ( filter == null || 
			 filter.getDefinition() == null || 
			 filter.getValue() == null ) return;
		
		predefinedFilters.add( filter );
	}
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		if(isFilterUsed("Utente")) {
			includeAuthorInQuery = true;
		}
		
		StringBuffer buffer = buildSelectClause(true);
		
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		includeAuthorInQuery = false;
		
		return result;
		
	}
	
	public Query getListQueryForStatistics( EntityManager entityManager ) {
		includeAuthorInQuery = true;
		
		StringBuffer buffer = buildSelectClause(false);
		
		writeFilters(buffer);
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		includeAuthorInQuery = false;
		
		return result;
		
	}
	
	@Override
	public Query getListQuery( EntityManager entityManager ) {
		if(isFilterUsed("Utente")) {
			includeAuthorInQuery = true;
		}
		
		StringBuffer buffer = buildSelectClause(false);
		
		// FIXME la query mi torna meno risultati di quanti dovrebbe -> ordinando per taxCode di patient, vengono trovate solo
		// le visite che hanno paziente != null, a differenza del count che invece le trova tutte.
		
		writeFilters(buffer);

		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		includeAuthorInQuery = false;
		
		return result;
	}
	
	private boolean isFilterUsed(String filterName) {
		for(Filter f : usedFilters()) {
			if(filterName.equals(f.getDefinition().getName())
					&& f.getValue() != null
					&& !"".equals(f.getValue())) {
				return true;
			}
		}
		
		return false;
	}
	
	// FIXME ottimizzare i link per aumentare le performance
	// FIXME serve ancora il join con i services ?
	private StringBuffer buildSelectClause(boolean count) {
		StringBuffer buffer = new StringBuffer();
		if(count) {
			buffer.append( "select count( distinct e ) " );
		}
		else {
			buffer.append( "select distinct e " );
		}
		
		if(includeAuthorInQuery) {
			buffer.append(" from Examination e, FactImpl f ")
//					.append("join e.appointment.services s ")
					.append("join f.context c ");
		}
		else {
			buffer.append(" from Examination e ")
//					.append("join e.appointment.services s ")
					;
		}
		
		return buffer;
	}
	
	private void writeFilters( StringBuffer buffer ) {
		boolean initWhere = true;
		
		if(includeAuthorInQuery) {
			initWhere = false;
			buffer.append( " WHERE " ).
				append("c.id = e.id AND ").
				append("f.type = e.type.type AND ").
				append("f.destination is null ");
		}
		
		for ( Filter filter : usedFilters() ) {
			if ( initWhere ) {
				initWhere = false;
				buffer.append( " where " );
			} else {
				buffer.append( " and " );
			}
			buffer.append( filter.getDefinition().getExpression() );
		}
	}
	
	private void resolveParameters( Query query ) {
		for ( Filter filter : usedFilters() ) {
			filter.resolveParameter( query );
		}
	}
	private String getSorting() {
		return examinationFilter.getSorting();
	}
	
	
	private Set<Filter> usedFilters() {
		Set<Filter> result = new HashSet<Filter>();
		
		for ( Filter f : getPredefinedFilters() ) {
			if ( f.getDefinition() != null && f.getValue() != null ) {
				result.add( f );
			}
		}
		for ( Filter f : examinationFilter.getFilters() ) {
			if ( f.getDefinition() != null && f.getValue() != null ) {
				result.add( f );
			}
		}
		
		return result;
	}
	
	protected Filter createFilter( String expression, String paramName, Object paramValue ) {
		FilterDef filterDef = new FilterDef( UUID.randomUUID().toString(), FilterType.COMBO, null, expression, paramName, null );
		Filter result = new Filter();
		result.setDefinition( filterDef );
		result.setValue( paramValue );
		return result;
	}
	
}
