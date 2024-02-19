package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeQueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.Type;

@Named
@SessionScoped
public class ExaminationTypeFilter extends FilterBean implements ExaminationTypeQueryBuilder {

	//
	// EJB injection
	//
	@Inject
	private TypeDao typeDao;

	private static final long serialVersionUID = 1684278027838269005L;

	public ExaminationTypeFilter() {
		setPageSize( 10 );

		// filters available
		initFilters();
		// sorting available
		initSorting();
	}

	private void initFilters() {
		initFilterDefs();
		initDefaultFilters();
	}

	private void initFilterDefs() {
		addFilterDef( "Name", FilterType.TEXT, "et.name like :etname", "etname" );

		addFilterDef( "Type", FilterType.SUGGESTION, "et.type.uuid = :ptype", "ptype",
				new SelectItemBuilder() {
					@Override
					public List<SelectItem> getSelectItems( Object param, int offset, int limit ) {
						List<SelectItem> items = new ArrayList<SelectItem>();
						List<Type> types = typeDao.findBySuggestion( param.toString(), limit );

						for ( Type t : types ) {
							items.add( new SelectItem( t.getUuid(), t.getName() ) );
						}
						return items;
					}
				} );

		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}

	private void initSorting() {
		addSort( "Name", "et.name asc", "et.name desc" );
		addSort( "Type", "et.type.name asc, et.name asc", "et.type.name desc, et.name asc" );

		// default sorting
		toggle( "Name" );
	}

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append( "select count( distinct et ) from ExaminationType et" );
		writeFilters( buffer );

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );

		return result;

	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append( "select distinct et from ExaminationType et" );

		writeFilters( buffer );

		if ( getSorting() != null ) {
			buffer.append( " order by " ).append( getSorting() );
		}

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );

		return result;
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
