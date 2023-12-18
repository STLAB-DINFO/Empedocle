package it.unifi.ing.stlab.reflection.converter;

import it.unifi.ing.stlab.reflection.dao.types.UnitDao;
import it.unifi.ing.stlab.reflection.model.types.Unit;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SimpleUnitConverter implements Converter {
	
	@Inject
	private UnitDao unitDao;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		return unitDao.findByUuid(value);
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if(!(object instanceof Unit)) {
			return null;
		}
		
		return ((Unit)object).getUuid();
		
	}

}
