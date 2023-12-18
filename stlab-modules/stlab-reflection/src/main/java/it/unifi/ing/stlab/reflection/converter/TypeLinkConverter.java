package it.unifi.ing.stlab.reflection.converter;

import it.unifi.ing.stlab.reflection.dao.types.links.TypeLinkDao;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TypeLinkConverter implements Converter {

	@Inject
	private TypeLinkDao typeLinkDao;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return typeLinkDao.findByUuid(value);
	}

	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(!(value instanceof Type))
			return null;
		
		return ((TypeLink)value).getUuid();
	}

}
