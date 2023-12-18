package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.FieldUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

public class OutputMeasurementUnitControllerTest {
	
	protected OutputMeasurementUnitController controller;
	
	protected FacesContext facesContext;
	
	@Before
	public void setUp() {
		controller = new OutputMeasurementUnitController();
		
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}	
	
	@Test
	public void testOsservazioneQuantitativaMeasurementUnit() throws Exception {
		QuantitativeFact qnt = FactFactory.createQuantitative();
		qnt.assignType(TypeFactory.createQuantitativeType());
		Unit u = UnitFactory.createUnit();
		u.setName("cm");
		u.setSimbol("cm");
		qnt.setQuantity( new Quantity( 180.0d, u));
		
		String result = controller.getMeasurementUnit(qnt);
		
		assertNotNull( result );
		assertEquals( "cm", result );
	}	
	
	@Test
	public void testOsservazioneTestualeMeasurementUnit() throws Exception {
		TextualFact txt = FactFactory.createTextual();
		txt.assignType(TypeFactory.createTextualType());
		txt.setText("hello world");
		
		controller.getMeasurementUnit(txt);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));

	}
	
	@Test
	public void testOsservazioneNullMeasurementUnit() throws Exception {
		controller.getMeasurementUnit(null);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));

	}
}
