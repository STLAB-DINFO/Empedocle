package it.unifi.ing.stlab.commons.faces;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class ExternalContextProducer {

	@Inject
	private FacesContext facesContext;

	
	@Produces @RequestScoped
	public ExternalContext getFacesContext() {
		ExternalContext context = facesContext.getExternalContext();
		
    	if (context == null)
    		throw new ContextNotActiveException("ExternalContext is not active");
    	
    	return context;
	}

}
