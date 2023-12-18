package it.unifi.ing.stlab.filters;

import it.unifi.ing.stlab.navigation.NavigationStatus;
import it.unifi.ing.stlab.sorting.Sorting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

public class FilterBean extends NavigationStatus implements Serializable, Converter {

	private static final long serialVersionUID = -4616695981146417136L;
	
	private Set<FilterDef> filterDefs;
	private List<Filter> filters;
	private Sorting sorting;
	private FilterDefsOrder filterDefsOrder;

	public FilterBean() {
		filterDefs = new LinkedHashSet<FilterDef>();
		filters = new ArrayList<Filter>();
		sorting = new Sorting();
		filterDefsOrder = FilterDefsOrder.ALPHABETICAL;
	} 
	
	protected void addFilterDef( String name, FilterType type, String expression, String param  ) {
		filterDefs.add( new FilterDef( UUID.randomUUID().toString(), type, name, expression, param, null ));
	}

	protected void addFilterDef( String name, FilterType type, String expression, String param, SelectItemBuilder selectItemBuilder ) {
		filterDefs.add( new FilterDef( UUID.randomUUID().toString(), type, name, expression, param, selectItemBuilder ));
	}
	
	protected void addSort( String name, String ascending, String descending ) {
		sorting.add(name, ascending, descending);
	}

	
	//
	// Filter methods
	//
	
	public Set<FilterDef> getFilterDefs() {
		return Collections.unmodifiableSet( filterDefs );
	}

	
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public boolean canAddFilter() {
		return filterDefs.size() > filters.size();
	}
	public void addFilter() {
		filters.add( new Filter() );
	}
	public void removeFilter( Filter filter ) {
		filters.remove( filter );
		clear();
	}
	
	public List<SelectItem> getFilterDefs( Filter filter ) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		
		for ( FilterDef filterDef : filterDefs ) {
			if ( filterDef.equals( filter.getDefinition() ) || !isUsed( filterDef )) {
				result.add( new SelectItem( filterDef, filterDef.getName() ));
			}
		}
		if(filterDefsOrder == FilterDefsOrder.ALPHABETICAL) {
			Collections.sort( result, new Comparator<SelectItem>() {
				@Override
				public int compare(SelectItem o1, SelectItem o2) {
					return o1.getLabel().compareTo( o2.getLabel() );
				}});
		}

		return result;
	}
	
	public void resetFilters(){
		filters.clear();
		initDefaultFilters();
	}
	
	protected void initDefaultFilters(){}
	
	private boolean isUsed( FilterDef filterDef ) {
		for ( Filter filter : filters ) {
			if ( filterDef.equals( filter.getDefinition() )) {
				return true;
			}
		}
		return false;
	}
	
	public FilterDef findFilterDefByName(String name) {
		if(name != null) {
			for( FilterDef def : filterDefs ) {
				if(name.equalsIgnoreCase( def.getName() ) ) {
					return def;
				}
			}
		}
		
		return null;
	}
	
	public void resolveParameters( Query query ) {
		for ( Filter filter : getFilters() ) {
			filter.resolveParameter( query );
		}
	}
	
	//
	// Sorting methods
	//
	public void toggle(String fieldName) {
		sorting.toggle(fieldName);
	}
	public boolean isSelected(String fieldName) {
		return sorting.isSelected(fieldName);
	}
	public boolean isAscending(String fieldName) {
		return sorting.isAscending(fieldName);
	}
	public String getSorting() {
		return sorting.getSorting();
	}
	
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		for ( FilterDef filterDef : getFilterDefs() ) {
			if ( filterDef.getUuid().equals( value )) {
				return filterDef;
			}
		}
		return null;
	}


	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (!(value instanceof FilterDef)) return null;
	    
	    return ((FilterDef)value).getUuid();
	}
	
	public FilterDefsOrder getFilterDefsOrder() {
		return filterDefsOrder;
	}

	public void setFilterDefsOrder(FilterDefsOrder filterDefsOrder) {
		this.filterDefsOrder = filterDefsOrder;
	}

	public enum FilterDefsOrder {
		INSERTION,
		ALPHABETICAL;
	}
	
}
