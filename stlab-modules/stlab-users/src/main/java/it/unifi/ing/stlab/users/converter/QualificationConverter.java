package it.unifi.ing.stlab.users.converter;

import it.unifi.ing.stlab.users.dao.QualificationDao;
import it.unifi.ing.stlab.users.model.Qualification;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class QualificationConverter implements Converter {

	@Inject
	private QualificationDao qualificationDao;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return qualificationDao.findByUuid(value);
	}

	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(!(value instanceof Qualification))
			return null;
		
		return ((Qualification)value).getUuid();
	}
	
}
