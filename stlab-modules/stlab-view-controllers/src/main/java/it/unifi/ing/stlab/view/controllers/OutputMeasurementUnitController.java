package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OutputMeasurementUnitController {

	@Inject
	private FacesContext facesContext;
	
	public String getMeasurementUnit(Fact f) {
		if ( f != null && ClassHelper.instanceOf( f, QuantitativeFact.class )) {
			QuantitativeFact ossQnt = ClassHelper.cast( f, QuantitativeFact.class );
			return ossQnt.getQuantity() != null && ossQnt.getQuantity().getUnit() != null ? 
					ossQnt.getQuantity().getUnit().getSimbol() : "";

		} else {
			if(f == null){
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_WARN, "Nessuna osservazione da mostrare", ""));
			} else{
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_ERROR, 
								"outputMeasurementUnit non supportato per "+ f.getType().getClass().getSimpleName(), ""));
			}
			return null;
		}
	}
}