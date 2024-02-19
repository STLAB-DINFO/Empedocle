package it.unifi.ing.stlab.view.actions;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;

@Named
@SessionScoped
public class ViewerFilter extends FilterBean implements QueryBuilder {

	private static final long serialVersionUID = -4354999655887420300L;

	public ViewerFilter() {
		setPageSize( 10 );

		// filters available
		initFilters();
		// sorting available
		initSorting();
	}

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append( "select count( distinct v ) from Viewer v where v.anonymous = false " );
		writeFilters( buffer );

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );

		return result;
	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append( "select distinct v from Viewer v where v.anonymous = false " );
		writeFilters( buffer );

		if ( getSorting() != null ) {
			buffer.append( " order by " ).append( getSorting() );
		}

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );

		return result;
	}

	private void writeFilters( StringBuffer buffer ) {
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed() ) {
				buffer.append( " and " );
				buffer.append( filter.getDefinition().getExpression() );
			}
		}
	}

	//
	// private methods
	//
	private void initFilters() {
		addFilterDef( "Name", FilterType.TEXT, "v.name like :pname", "pname" );
		addFilterDef( "Type", FilterType.TEXT, "v.type.name like :tname", "tname" );

		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}

	private void initSorting() {
		addSort( "Name", "v.name asc", "v.name desc" );
		addSort( "Type", "v.type.name asc, v.name asc", "v.type.name desc, v.name asc" );
		toggle( "Name" );
	}
}
