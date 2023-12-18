package it.unifi.ing.stlab.users.converter;

import it.unifi.ing.stlab.users.dao.RoleDao;
import it.unifi.ing.stlab.users.model.Role;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class RoleConverter implements Converter {
	
	@Inject
	private RoleDao roleDao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || "".equals(value.trim())) {
            return null;
        }

        Role role = roleDao.findByUuid(value);

        if (role == null) {
            throw new ConverterException(new FacesMessage("Unknown Role UUID: " + value));
        }
        
        return role;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null || !(value instanceof Role)) {
			return null;
		}
		
		return ((Role) value).getUuid();
	}

}
