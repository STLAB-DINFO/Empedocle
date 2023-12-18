package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.AltezzaPesoController;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class AltezzaPesoControllerTest {

	protected AltezzaPesoController controller;
	protected ViewerCustom viewer;
	private QuantitativeFact altezza;
	private QuantitativeFact peso;
	private QuantitativeFact sc;
	private QuantitativeFact imc;
	private TypeSelector selector;
	
	@Before
	public void setUp() throws Exception{
		selector = mock(TypeSelector.class);
		
		viewer = mock(ViewerCustom.class);
		ViewerLink vl = new SubViewer(UUID.randomUUID().toString());
		vl.setSelector(selector);
		when(viewer.getByPriority(anyLong())).thenReturn(vl);
		
		controller = new AltezzaPesoController();
		
		altezza = FactFactory.createQuantitative();
		peso = FactFactory.createQuantitative();
		sc = FactFactory.createQuantitative();
		imc = FactFactory.createQuantitative();

		Quantity quantityAltezza = new Quantity();
		quantityAltezza.setValue(new Double(180));
		Unit u = UnitFactory.createUnit();
		u.setSimbol("cm");
		quantityAltezza.setUnit(u);
		altezza.setQuantity(quantityAltezza);
		
		Quantity quantityPeso = new Quantity();
		quantityPeso.setValue(new Double(90));
		peso.setQuantity(quantityPeso);
		
		Quantity quantitySC = new Quantity();
		quantitySC.setValue(new Double(0));
		sc.setQuantity(quantitySC);
		
		Quantity quantityIMC = new Quantity();
		quantityIMC.setValue(new Double(0));
		imc.setQuantity(quantityIMC);
		
		FieldUtils.assignField(controller, "altezza", altezza);
		FieldUtils.assignField(controller, "peso", peso);
		FieldUtils.assignField(controller, "sc", sc);
		FieldUtils.assignField(controller, "imc", imc);
	}
	
	@Test
	public void testRetrieveSC(){
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		
		controller.retrieveSC(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getSc().getQuantity());
		assertNotNull(controller.getSc().getQuantity().getValue());
		assertEquals(new Double(2.12), controller.getSc().getQuantity().getValue());
		
	}
	
	@Test
	public void testAltezzaNull() {
		altezza.getQuantity().setValue(null);
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		
		controller.retrieveSC(FactFactory.createComposite(), viewer);
		controller.retrieveIMC(FactFactory.createComposite(), viewer);
		
		assertNull(controller.getSc().getQuantity().getValue());
		assertNull(controller.getImc().getQuantity().getValue());
	}
	
	@Test
	public void testPesoNull() {
		peso.getQuantity().setValue(null);
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		
		controller.retrieveSC(FactFactory.createComposite(), viewer);
		controller.retrieveIMC(FactFactory.createComposite(), viewer);

		assertNull(controller.getSc().getQuantity().getValue());
		assertNull(controller.getImc().getQuantity().getValue());
	}
	
	@Test
	public void testAllNull() {
		peso.getQuantity().setValue(null);
		altezza.getQuantity().setValue(null);
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		
		controller.retrieveSC(FactFactory.createComposite(), viewer);
		controller.retrieveIMC(FactFactory.createComposite(), viewer);

		assertNull(controller.getSc().getQuantity().getValue());
		assertNull(controller.getImc().getQuantity().getValue());
	}
	
	@Test
	public void testRetrieveIMC(){
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		controller.retrieveIMC(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getImc().getQuantity());
		assertNotNull(controller.getImc().getQuantity().getValue());
		assertEquals(new Double(27.78), controller.getImc().getQuantity().getValue());
		
	}
	
	@Test
	public void testAltezzaZero(){
		peso.getQuantity().setValue( 10d );
		altezza.getQuantity().setValue( 0d );
		
		when(selector.apply(any(Fact.class))).thenReturn(sc);
		
		controller.retrieveIMC(FactFactory.createComposite(), viewer);
		assertNull(controller.getImc().getQuantity().getValue());
	}
	
}
