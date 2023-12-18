package it.unifi.ing.stlab.filters;

import java.util.List;

import javax.faces.model.SelectItem;

public interface SelectItemBuilder {

	public List<SelectItem> getSelectItems(Object param, int offset, int limit);
	
}
