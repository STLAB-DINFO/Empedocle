package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OutputValueController {

	@Inject
	private FacesContext facesContext;
	
	public String getTextualValue(Fact f) {
		if (f != null && ClassHelper.instanceOf( f, TextualFact.class )) {
			return ClassHelper.cast( f, TextualFact.class ).getText();

		} else if (f != null && ClassHelper.instanceOf( f, QualitativeFact.class )) {
			QualitativeFact ossQlt = ClassHelper.cast( f, QualitativeFact.class );
			return ossQlt.getPhenomenon() != null ? ossQlt.getPhenomenon().getName() : "";

		} else {
			if(f == null){
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_WARN, "No observation to show" , ""));
			} else{
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_ERROR, 
								"outputValue non supportato per " + f.getType().getClass().getSimpleName(), ""));
			}
			
			return null;
		}
	}
	
	public Date getDateValue(Fact f) {
		if ( f != null && ClassHelper.instanceOf( f, TemporalFact.class )) {
			TemporalFact ossQnt = ClassHelper.cast( f, TemporalFact.class );
			return ossQnt.getDate();

		} else {
			if(f == null){
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_WARN, "No observation to show", ""));
			} else{
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_ERROR, 
								"outputValue non supportato per " + f.getType().getClass().getSimpleName(), ""));
			}
			
			return null;
		}
	}
	
	public Double getNumericValue(Fact f) {
		if ( f != null && ClassHelper.instanceOf( f, QuantitativeFact.class )) {
			QuantitativeFact ossQnt = ClassHelper.cast( f, QuantitativeFact.class );
			return ossQnt.getQuantity() != null && ossQnt.getQuantity().getValue() != null ? 
					ossQnt.getQuantity().getValue() : null;

		} else {
			if(f == null){
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_WARN, "No observation to show", ""));
			} else{
				facesContext.addMessage( null, 
						new FacesMessage( FacesMessage.SEVERITY_ERROR, 
								"outputValue non supportato per " + f.getType().getClass().getSimpleName(), ""));
			}
			
			return null;
		}
	}
	
	public boolean isQuantitativeFact(Fact fact) {
		return fact != null && ClassHelper.instanceOf( fact, QuantitativeFact.class );
	}
	
	public boolean isTemporalFact(Fact fact) {
		return fact != null && ClassHelper.instanceOf( fact, TemporalFact.class );
	}
}
