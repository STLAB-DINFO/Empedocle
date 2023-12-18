package it.unifi.ing.stlab.reflection.actions;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.reflection.dao.types.TypeQueryBuilder;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;

@Named
@SessionScoped
public class TypeFilter extends FilterBean implements TypeQueryBuilder {

	private static final long serialVersionUID = -754640929603136760L;

	private TypeListType selectedList;
	private Map<TypeListType, TypeQueryBuilder> queryBuilders;

	public TypeFilter() {
		setPageSize( 10 );

		// filters available
		initFilters();
		// sorting available
		initSorting();
		// query builders
		initQueryBuilders();

		selectedList = TypeListType.ALL;
	}

	private void initQueryBuilders() {
		queryBuilders = new HashMap<TypeListType, TypeQueryBuilder>();

		queryBuilders.put( TypeListType.ALL, new TypeQueryBuilderImpl( Type.class, this ) );
		queryBuilders.put( TypeListType.TEXTUAL,
				new TypeQueryBuilderImpl( TextualType.class, this ) );
		queryBuilders.put( TypeListType.ENUMERATED,
				new TypeQueryBuilderImpl( EnumeratedType.class, this ) );
		queryBuilders.put( TypeListType.QUERIED,
				new TypeQueryBuilderImpl( QueriedType.class, this ) );
		queryBuilders.put( TypeListType.QUANTITATIVE,
				new TypeQueryBuilderImpl( QuantitativeType.class, this ) );
		queryBuilders.put( TypeListType.COMPOSITE,
				new TypeQueryBuilderImpl( CompositeType.class, this ) );
		queryBuilders.put( TypeListType.TEMPORAL,
				new TypeQueryBuilderImpl( TemporalType.class, this ) );
	}

	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		return queryBuilders.get( selectedList ).getCountQuery( entityManager );
	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		return queryBuilders.get( selectedList ).getListQuery( entityManager );
	}

	public void selectList( String listType ) {
		for ( TypeListType type : TypeListType.values() ) {
			if ( type.toString().equalsIgnoreCase( listType ) ) {
				selectedList = type;
				clear();
			}
		}
	}

	public boolean isSelectedList( String listType ) {
		return selectedList.toString().equalsIgnoreCase( listType );
	}

	//
	// private methods
	//
	private void initFilters() {
		addFilterDef( "Nome", FilterType.TEXT, "t.name like :pname", "pname" );
		addFilterDef( "Ricorrente", FilterType.BOOLEAN, "t.recurrent = :precurrent", "precurrent");

		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}

	private void initSorting() {
		addSort( "Nome", "t.name asc", "t.name desc" );
		addSort( "Ricorrente", "t.recurrent asc, t.name asc", "t.recurrent desc, t.name asc" );
		toggle( "Nome" );
	}
}
