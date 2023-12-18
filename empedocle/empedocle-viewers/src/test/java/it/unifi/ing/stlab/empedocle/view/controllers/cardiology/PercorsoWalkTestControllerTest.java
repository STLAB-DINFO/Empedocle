package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.PercorsoWalkTestController;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class PercorsoWalkTestControllerTest {

	private PercorsoWalkTestController controller;
	private QuantitativeFact numeroVolteCorridoio;
	private QuantitativeFact numeroTacche;
	private QuantitativeFact metriPercorsi;
	
	@Before
	public void setUp() throws Exception{
		controller = new PercorsoWalkTestController();
		
		numeroVolteCorridoio = FactFactory.createQuantitative();
		Quantity quantityCorridoio = new Quantity();
		quantityCorridoio.setValue(new Double(7));
		numeroVolteCorridoio.setQuantity(quantityCorridoio);
		
		numeroTacche = FactFactory.createQuantitative();
		Quantity quantityTacche = new Quantity();
		quantityTacche.setValue(new Double(10));
		numeroTacche.setQuantity(quantityTacche);
		
		metriPercorsi = FactFactory.createQuantitative();
		Quantity quantityMetri = new Quantity();
		quantityMetri.setValue(new Double(0));
		metriPercorsi.setQuantity(quantityMetri);
		
		FieldUtils.assignField(controller, "numeroVolteCorridoio", numeroVolteCorridoio);
		FieldUtils.assignField(controller, "numeroTacche", numeroTacche);
		
	}
	
	
	@Test
	public void retrieveMetriPercorsi(){
		TypeSelector selector = mock(TypeSelector.class);
		when(selector.apply(any(Fact.class))).thenReturn( metriPercorsi );
		
		Viewer viewer = mock(ViewerCustom.class);
		ViewerLink vl = new SubViewer(UUID.randomUUID().toString());
		vl.setSelector(selector);
		when(viewer.getByPriority(anyLong())).thenReturn(vl);
		
		controller.retrieveMetriPercorsi(FactFactory.createComposite(), viewer);
		
		assertNotNull(controller.getMetriPercorsi());
		assertNotNull(controller.getMetriPercorsi().getQuantity().getValue());
		assertEquals(new Double(287.5), controller.getMetriPercorsi().getQuantity().getValue());
		
	}
	
}
