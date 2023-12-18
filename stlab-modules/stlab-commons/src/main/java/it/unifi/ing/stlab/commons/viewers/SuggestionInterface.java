package it.unifi.ing.stlab.commons.viewers;

import java.util.List;

import javax.faces.model.SelectItem;

public interface SuggestionInterface {

	public List<SelectItem> autocomplete(String suggestion);
	
}
