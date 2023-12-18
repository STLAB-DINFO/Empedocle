package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QualitativeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class SuggestionController implements Serializable {

	private static final long serialVersionUID = 7602105139214277064L;

	@Inject
	private PhenomenonDao phenomenonDao;
	
	@Inject
	private FacesContext facesContext;
	
	private QualitativeType selectedType;
	private static final int MAX_RESULTS = 25; //XXX extended to solve limitation in the results in the case of QueriedTypes 
											   // Better solution: add an additional field to QualitativeType named limit 
											   // (if null, by default 10 results are returned; otherwise, limit specifies the number of max results to be returned)
											   // This solution requires to extend the type.g to support the definition of the limit using the DSL
	
	public boolean isSupported(Fact fact){
		if(fact == null){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_WARN, "Nessuna osservazione da mostrare", ""));
			return false;
		}
		
		if( fact == null || !ClassHelper.instanceOf( fact, QualitativeFact.class )){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_ERROR, 
							"Suggestion non supportata per l'osservazione di tipo " + fact.getType().getClass().getSimpleName(), ""));
			return false;	
		}
		
		return true;
		
	}
	
	public List<SelectItem> autocomplete(String input){
		List<SelectItem> items = new ArrayList<SelectItem>();
		
		if(selectedType != null) {
			for(Phenomenon p : phenomenonDao.findBySuggestion(input, selectedType, new Date(), MAX_RESULTS)){
				items.add(new SelectItem(p.getUuid(), p.getName()));
			}
		}
		
		return items;
	}
	
	public void resetValue(Fact fact){
		ClassHelper.cast( fact, QualitativeFact.class ).setPhenomenon( null );
	}

	public void assignSelectedType(QualitativeType selectedType) {
		this.selectedType = selectedType;
	}

}