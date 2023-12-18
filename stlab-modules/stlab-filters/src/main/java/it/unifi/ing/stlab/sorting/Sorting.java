package it.unifi.ing.stlab.sorting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sorting {

	private Map<String, SortField> fields;
	private SortField selected;
	private boolean ascending;
	
	
	public Sorting() {
		fields = new HashMap<String, SortField>();
		selected = null;
		ascending = false;
	}
	
	
	public void toggle( String fieldName ) {
		SortField field = fields.get( fieldName );
		if ( field == null )
			throw new IllegalArgumentException();
		
		if ( field.equals( selected )) {
			ascending = !ascending;
		} else {
			selected = field;
			ascending = true;
		}
	}
	
	public boolean isSelected( String fieldName ) {
		if ( selected == null ) return false;
		
		return selected.getName().equals( fieldName );
	}
	
	public boolean isAscending( String fieldName ) {
		if ( selected == null ) return false;
		
		return selected.getName().equals( fieldName ) && ascending;
	}
	
	public String getSorting() {
		if ( selected == null ) return null;
		
		if ( ascending ) {
			return selected.getAscending();
		} else {
			return selected.getDescending();
		}
	}
	
	public void add( String name, String ascending, String descending ) {
		fields.put( name, new SortField( UUID.randomUUID().toString(), name, ascending, descending ));
	}
}
