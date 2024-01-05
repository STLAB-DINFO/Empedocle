package it.unifi.ing.stlab.filters;

import javax.faces.model.SelectItem;
import java.util.List;

public interface SelectItemBuilder {

	List<SelectItem> getSelectItems(Object param, int offset, int limit);
	
}
