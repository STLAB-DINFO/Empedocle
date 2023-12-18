package it.unifi.ing.stlab.filters;

import java.util.List;

import javax.faces.model.SelectItem;

public class FilterDef {

	private String uuid;
	private FilterType type;
	private String name;
	private String expression;
	private String param;
	private SelectItemBuilder selectItemBuilder;
	
	public FilterDef(String uuid, FilterType type, String name, String expression, String param, SelectItemBuilder selectItemBuilder ) {
		super();
		
		this.uuid = uuid;
		this.type = type;
		this.name = name;
		this.expression = expression;
		this.param = param;
		this.selectItemBuilder = selectItemBuilder;
	}
	public FilterDef() {
		super();
	}

	
	// UUID
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	// Type
	public FilterType getType() {
		return type;
	}
	public void setType(FilterType type) {
		this.type = type;
	}

	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	// Expression
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}

	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}

	
	public List<SelectItem> getSelectItems() {
		return selectItemBuilder.getSelectItems("", 0, 0);
	}

	public List<SelectItem> autocomplete(String suggestion) {
		return selectItemBuilder.getSelectItems(suggestion, 0, 0);
	}
	
	//
	// HashCode & Equals
	//
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uuid.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof FilterDef )) return false;

		FilterDef other = (FilterDef) obj;
		return uuid.equals( other.getUuid() );
	}

}
