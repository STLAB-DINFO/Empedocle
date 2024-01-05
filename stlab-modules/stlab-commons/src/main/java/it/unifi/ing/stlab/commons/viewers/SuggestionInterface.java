package it.unifi.ing.stlab.commons.viewers;

import javax.faces.model.SelectItem;
import java.util.List;

public interface SuggestionInterface {

	List<SelectItem> autocomplete(String suggestion);
	
}
