package it.unifi.ing.stlab.empedocle.actions.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class PlusSignConverter extends DecimalNumberConverter {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Integer nDecimals = getUnitUse(component).getDecimals();
		String formattedString = getUtils().formatDecimals((Number)value, nDecimals, nDecimals);
		
		if(!"".equals(formattedString)) {
			StringBuffer result = new StringBuffer();
			Number n = (Number)value;
			
			if(n.doubleValue() > 0) {
				result.append("+");
			}
			
			result.append(formattedString);
			
			return result.toString();
		}
		
		else {
			return formattedString;
		}
		
	}
	
}
