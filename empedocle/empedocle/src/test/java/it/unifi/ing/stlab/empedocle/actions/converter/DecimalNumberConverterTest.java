package it.unifi.ing.stlab.empedocle.actions.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.test.FieldUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DecimalNumberConverterTest {

	private DecimalNumberConverter converter;
	
	private final Integer n_decimals = 2;
	
	@Mock private TypeDao typeDao;
	@Mock private FacesContext context;
	@Mock private UnitUse selectedUnit;
	
	private UIComponent component;
	
	@Before
	public void setUp() {
		converter = new DecimalNumberConverter();
		component = new UIInput();
		component.getAttributes().put("unitId", "1");
		component.getAttributes().put("typeId", 1L);
		
		when(typeDao.fetchWithUnitUses(anyLong())).thenReturn(TypeFactory.createQuantitativeType());
		when(selectedUnit.getDecimals()).thenReturn(n_decimals);
		
		FieldUtils.assignField(converter, "typeDao", typeDao);
		FieldUtils.assignField(converter, "selectedUnit", selectedUnit);
		
	}
	
	@Test
	public void testGetAsObject() {
		String value = "1.123";
		Double result = (Double) converter.getAsObject(context, component, value);
		assertEquals(new Double(1.12), result);
		
		value = "1.1";
		result = (Double) converter.getAsObject(context, component, value);
		assertEquals(new Double(1.1), result);
		
		value = "1";
		result = (Double) converter.getAsObject(context, component, value);
		assertEquals(new Double(1), result);
		
	}
	
	@Test
	public void testGetAsString() {
		Number value = new Double(1.123);
		String outcome = converter.getAsString(context, component, value);
		assertEquals("1.12", outcome);
		
		value = new Double(1.1);
		outcome = converter.getAsString(context, component, value);
		assertEquals("1.1", outcome);
		
		value = new Double(1);
		outcome = converter.getAsString(context, component, value);
		assertEquals("1", outcome);
		
	}

}
