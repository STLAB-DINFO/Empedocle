package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.FieldUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;


public class OutputValueControllerTest {
	
	protected OutputValueController controller;
	protected FacesContext facesContext;

	@Before
	public void setUp() {
		controller = new OutputValueController();
		facesContext = mock(FacesContext.class);
		FieldUtils.assignField(controller, "facesContext", facesContext);
	}	
	
	@Test
	public void testOsservazioneTestualeValue() throws Exception {
		TextualFact txt = FactFactory.createTextual();
		txt.assignType(TypeFactory.createTextualType());
		txt.setText("hello world");
		
		String result = controller.getTextualValue(txt);
		
		assertNotNull( result );
		assertEquals( "hello world", result );
	}
	
	@Test
	public void testOsservazioneQuantitativaValue() throws Exception {
		QuantitativeFact qnt = FactFactory.createQuantitative();
		qnt.assignType(TypeFactory.createQuantitativeType());
		Unit u = UnitFactory.createUnit();
		u.setName("cm");
		u.setSimbol("cm");
		qnt.setQuantity( new Quantity( 180.0d, u));
		
		Double result = controller.getNumericValue(qnt);
		
		assertNotNull( result );
		assertEquals( new Double(180.0), result );
	}
	
	@Test
	public void testOsservazioneQualitativaValue() throws Exception {
		QualitativeFact qlt = FactFactory.createQualitative();
		qlt.assignType(TypeFactory.createEnumeratedType());
		Phenomenon p = PhenomenonFactory.createPhenomenon();
		p.setName("fenomeno1");
		qlt.setPhenomenon(p);
		
		String result = controller.getTextualValue(qlt);
		
		assertNotNull( result );
		assertEquals( "fenomeno1", result );
	}	
	
	@Test
	public void testOsservazioneCompositaValue() throws Exception {
		CompositeFact cmp = FactFactory.createComposite();
		cmp.assignType(TypeFactory.createCompositeType());
		
		controller.getTextualValue(cmp);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));
	}
	
	@Test
	public void testOsservazioneNullMeasurementUnit() throws Exception {
		controller.getTextualValue(null);
		verify(facesContext).addMessage(anyString(), any(FacesMessage.class));

	}
	
}
