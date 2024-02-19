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
public class OutputLinkController {

	@Inject
	private FacesContext facesContext;
	
	public boolean isSupported(Fact fact){
		if(fact == null){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_WARN, "No observation to show", ""));
			return false;
		}
		if(!isTextualFact(fact)){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_ERROR, "OutputLinkController not supported for the observation of type " +
					fact.getType().getClass().getSimpleName(), ""));
			return false;
		}
		return true;
	}
	
	public boolean isTextualFact(Fact fact) {
		return fact != null && ClassHelper.instanceOf( fact, TextualFact.class );
	}
}
