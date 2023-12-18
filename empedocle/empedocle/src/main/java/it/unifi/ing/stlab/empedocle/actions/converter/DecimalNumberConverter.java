package it.unifi.ing.stlab.empedocle.actions.converter;

import it.unifi.ing.stlab.commons.util.NumberFormatUtils;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DecimalNumberConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;
	
	private UnitUse selectedUnit;
	protected NumberFormatUtils utils;
	
	public DecimalNumberConverter() {
		utils = new NumberFormatUtils();
	}
	
	@Inject
	protected TypeDao typeDao;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if(value == null || "".equals(value) || !utils.isNumeric(value)) {
			return null;
		}
		else {
			return convertValueAsDouble(value, getUnitUse(component).getDecimals());
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value == null) {
			return "";
		}
		else {
			return formatValueAsString((Number)value, 0, getUnitUse(component).getDecimals());
		}
	}
	
	protected String formatValueAsString(Number value, Integer minDecimals, Integer maxDecimals) {
		return utils.formatDecimals(value, minDecimals, maxDecimals);
	}
	
	protected Double convertValueAsDouble(String value, Integer maxDecimals) {
		return utils.formatDouble(value, maxDecimals);
	}
	
	protected UnitUse getUnitUse(UIComponent component) {
//		if(selectedUnit == null) {
		initSelectedUnit(component);
//		}
		return selectedUnit;
	
	}

	private void initSelectedUnit(UIComponent component) {
		String unitUUID = (String)component.getAttributes().get("unitId");
		Long typeId = (Long)component.getAttributes().get("typeId");
		
		QuantitativeType type = typeDao.fetchWithUnitUses(typeId);
		
		for(UnitUse u : type.listUnits()) {
			if(u.getUnit().getUuid().equals(unitUUID)) {
				selectedUnit = u;
				break;
			}
		}
		
	}

	public NumberFormatUtils getUtils() {
		return utils;
	}
	
}
