package it.unifi.ing.stlab.filters;

import it.unifi.ing.stlab.entities.utils.DateHelper;

import java.util.Date;

import javax.persistence.Query;

public class Filter {

	private FilterDef definition;
	private Object value;
	private String suggestion;

	
	public Filter() {
		super();
	}

	
	// Definition
	public FilterDef getDefinition() {
		return definition;
	}
	public void setDefinition(FilterDef definition) {
		if ( this.definition == null || !this.definition.equals( definition )) {
			value = null;
		}
		this.definition = definition;
	}


	// Value
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}


	public boolean isUsed() {
		return definition != null && value != null;
	}

	public void clear() {
		value = null;
	}

	
	public void resolveParameter( Query query ) {
		if ( definition == null || value == null ) return;

		switch ( definition.getType() ) {
		case TEXT:
			query.setParameter( definition.getParam(), "%" + value + "%" );
			break;
		case DATE:
			query.setParameter( definition.getParam(), DateHelper.startOfToday( (Date)value ) );
			break;
		case COMBO:
			query.setParameter( definition.getParam(), value );
			break;
		case SUGGESTION:
			query.setParameter( definition.getParam(), value );
			break;			
		case BOOLEAN:
			query.setParameter( definition.getParam(), Boolean.valueOf( (String)value) );
			break;
		}
	}


	public String getSuggestion() {
		return suggestion;
	}


	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
