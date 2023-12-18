package it.unifi.ing.stlab.empedocle.actions.staff;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;
import it.unifi.ing.stlab.users.dao.QualificationDao;
import it.unifi.ing.stlab.users.dao.RoleDao;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.Role;

@Named
@SessionScoped
public class StaffFilter extends FilterBean implements QueryBuilder {

	private static final long serialVersionUID = 1649905706480798954L;

	//
	// EJB injection
	//
	@Inject
	private QualificationDao qualificationDao;

	@Inject
	private RoleDao roleDao;

	public StaffFilter() {
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

	private void initSorting() {
		addSort( "Cognome", "s.user.surname asc, s.user.name asc",
				"s.user.surname desc, s.user.name asc" );
		// default sorting
		toggle( "Cognome" );
	}

	private void initFilterDefs() {
		addFilterDef( "Cognome", FilterType.TEXT, "s.user.surname like :psurname", "psurname" );
		addFilterDef( "Nome", FilterType.TEXT, "s.user.name like :pname", "pname" );

		addFilterDef( "Ruolo", FilterType.SUGGESTION, "r.uuid = :prole", "prole",
				new SelectItemBuilder() {
					@Override
					public List<SelectItem> getSelectItems( Object param, int offset, int limit ) {
						List<SelectItem> items = new ArrayList<SelectItem>();
						List<Role> roles = roleDao.findBySuggestion( param.toString(), limit );

						for ( Role r : roles ) {
							items.add( new SelectItem( r.getUuid(), r.getName() ) );
						}
						return items;
					}
				} );

		addFilterDef( "Qualifica", FilterType.SUGGESTION, "q.uuid = :pqual", "pqual",
				new SelectItemBuilder() {
					@Override
					public List<SelectItem> getSelectItems( Object param, int offset, int limit ) {
						List<SelectItem> items = new ArrayList<SelectItem>();
						List<Qualification> qualifications = qualificationDao
								.findBySuggestion( param.toString(), limit );

						for ( Qualification q : qualifications ) {
							items.add( new SelectItem( q.getUuid(), q.getName() ) );
						}
						return items;
					}
				} );

		addFilterDef( "Mostra utenti disattivati", FilterType.BOOLEAN,
				"s.user.isDeprecated = :pdeprecated", "pdeprecated" );

		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}

	@Override
	protected void initDefaultFilters() {
		Filter disabledFilter = new Filter();
		disabledFilter.setDefinition( findFilterDefByName( "Mostra utenti disattivati" ) );
		disabledFilter.setValue( String.valueOf( false ) );

		getFilters().add( disabledFilter );
	}

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(
				"select count( distinct s ) from Staff s left join s.user.roles r left join s.user.qualifications q left join s.agendas a " );
		writeFilters( buffer );

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );

		return result;
	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(
				"select distinct s from Staff s left join s.user.roles r left join s.user.qualifications q left join s.agendas a " );

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
