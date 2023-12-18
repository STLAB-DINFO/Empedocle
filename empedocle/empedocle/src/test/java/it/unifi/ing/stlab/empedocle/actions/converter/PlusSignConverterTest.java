package it.unifi.ing.stlab.empedocle.actions.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.commons.util.NumberFormatUtils;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlusSignConverterTest {

	@Mock private PlusSignConverter converter;
	
	private final Integer n_decimals = 2;
	
	@Mock private TypeDao typeDao;
	@Mock private FacesContext context;
	@Mock private UnitUse selectedUnit;
	
	private UIComponent component;
	
	@Before
	public void setUp() {
		component = new UIInput();
		component.getAttributes().put("unitId", "1");
		component.getAttributes().put("typeId", 1L);
		
		when(converter.getUnitUse(component)).thenReturn(selectedUnit);
		when(selectedUnit.getDecimals()).thenReturn(n_decimals);
		when(converter.getUtils()).thenReturn(new NumberFormatUtils());
		
	}
	
	@Test
	public void testGetAsString() {
		Number value = new Double(1.123);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		String outcome = converter.getAsString(context, component, value);
		assertEquals("+1.12", outcome);
		
		value = new Double(1.1);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		outcome = converter.getAsString(context, component, value);
		assertEquals("+1.10", outcome);
		
		value = new Double(1);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		outcome = converter.getAsString(context, component, value);
		assertEquals("+1.00", outcome);
		
		value = new Double(-1.123);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		outcome = converter.getAsString(context, component, value);
		assertEquals("-1.12", outcome);
		
		value = new Double(-1.1);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		outcome = converter.getAsString(context, component, value);
		assertEquals("-1.10", outcome);
		
		value = new Double(-1);
		when(converter.getAsString(context, component, value)).thenCallRealMethod();
		outcome = converter.getAsString(context, component, value);
		assertEquals("-1.00", outcome);
		
	}

}
