package it.unifi.ing.stlab.empedocle.actions.messages;

import it.unifi.ing.stlab.empedocle.dao.messages.MessageQueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterDef;
import it.unifi.ing.stlab.filters.FilterType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// TODO test MessageQueryBuilderImpl
public class MessageQueryBuilderImpl implements MessageQueryBuilder {

	private final MessageFilter messageFilter;
	private Set<Filter> predefinedFilters;
	
	public MessageQueryBuilderImpl( MessageFilter messageFilter ) {
		this.messageFilter = messageFilter;
		predefinedFilters = new HashSet<Filter>();
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
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( m ) " )
			  .append( " from Message m" );
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
		
	} 
	
	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select m " )
			  .append( " from Message m");
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}
	
	private Set<Filter> usedFilters() {
		Set<Filter> result = new HashSet<Filter>();
		
		for ( Filter f : getPredefinedFilters() ) {
			if ( f.getDefinition() != null && f.getValue() != null ) {
				result.add( f );
			}
		}
		for ( Filter f : messageFilter.getFilters() ) {
			if ( f.getDefinition() != null && f.getValue() != null ) {
				result.add( f );
			}
		}
		
		return result;
	}
	
	private void writeFilters( StringBuffer buffer ) {
		boolean initWhere = true;
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
		return messageFilter.getSorting();
	}
	
	protected Filter createFilter( String expression, String paramName, Object paramValue ) {
		FilterDef filterDef = new FilterDef( UUID.randomUUID().toString(), FilterType.COMBO, null, expression, paramName, null );
		Filter result = new Filter();
		result.setDefinition( filterDef );
		result.setValue( paramValue );
		return result;
	}
	
}
