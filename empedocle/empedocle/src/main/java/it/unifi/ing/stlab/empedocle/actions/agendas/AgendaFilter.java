package it.unifi.ing.stlab.empedocle.actions.agendas;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;

@Named
@SessionScoped
public class AgendaFilter extends FilterBean implements QueryBuilder { 

	private static final long serialVersionUID = 4598634465719410383L;

	public AgendaFilter() {
		setPageSize( 10 );

		// filters available
		initFilters();
		// sorting available
		initSorting();
	}
	

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( a ) from Agenda a " );
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select a from Agenda a " );
		
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}
	
	
	//
	// private methods
	//
	private void initFilters() {
		addFilterDef( "Codice", FilterType.TEXT, "a.code like :pcode", "pcode" );
		addFilterDef( "Descrizione", FilterType.TEXT, "a.description like :pdex", "pdex" );

		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}
	
	private void initSorting() {
		addSort( "Codice", "a.code asc", "a.code desc" );
		// default sorting
		toggle( "Codice" );
	}


	private void writeFilters( StringBuffer buffer ) {
		boolean initWhere = true;
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed() ) {
				if ( initWhere ) {
					initWhere = false;
					buffer.append( " where " );
				} else {
					buffer.append( " and " );
				}
				buffer.append( filter.getDefinition().getExpression() );
			}
		}
	}

}
