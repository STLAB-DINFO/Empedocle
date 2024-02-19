package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TextAreaController {

	@Inject
	private FacesContext facesContext;
	
	public boolean isOsservazioneTestuale(Fact f) {
		if(f == null){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_WARN, "No observation to show", ""));
			return false;
		}
		
		if( f == null || !ClassHelper.instanceOf( f, TextualFact.class )){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_ERROR, 
							"TextArea non supportata per l'osservazione di tipo " + f.getType().getClass().getSimpleName(), ""));
			return false;	
		}
		
		return true;
	}
	
}
