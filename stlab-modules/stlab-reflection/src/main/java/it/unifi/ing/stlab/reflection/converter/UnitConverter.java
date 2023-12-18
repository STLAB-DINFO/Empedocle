package it.unifi.ing.stlab.reflection.converter;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class UnitConverter extends SimpleUnitConverter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent component, String value) {
		Fact fact = (Fact)component.getAttributes().get("fact");
		
		for(UnitUse uu : ClassHelper.cast(fact.getType(), QuantitativeType.class).listUnits()){
			if(uu.getUnit().getUuid().equals(value))
				return uu.getUnit();
		}
		 
		return null;
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		return super.getAsString(arg0, arg1, object);
		
	}

}
