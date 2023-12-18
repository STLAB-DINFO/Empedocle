package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OutputPathController {

	@Inject
	private FacesContext facesContext;
	
	public String getValue(Fact fact, TypeSelector selector) {
		
		if(selector == null || selector.getTypeLink() == null){
			facesContext.addMessage( null, new FacesMessage("outputPath necessita di un selettore"));
			return null;
		}
		
		Set<FactLink> filtered = selector.applyItem(fact);
		if(filtered == null || filtered.isEmpty()){
			facesContext.addMessage( null, new FacesMessage("Nessun elemento trovato per la selezione "+ error(selector)));
			return null;
		}
		
		return filtered.iterator().next().getType().getName();
		
	}
	
	private String error(TypeSelector selector) {
		StringBuilder builder = new StringBuilder();

		do {
			builder.append("\"" + selector.getTypeLink().getName() + "\".");
			selector = selector.getNext();
		} while ( selector != null );
		
		return builder.toString();
	}
	
}
