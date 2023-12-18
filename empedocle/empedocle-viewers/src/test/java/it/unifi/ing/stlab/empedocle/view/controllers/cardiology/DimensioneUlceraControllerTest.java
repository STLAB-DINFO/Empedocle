package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.DimensioneUlceraController;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class DimensioneUlceraControllerTest {

	private DimensioneUlceraController controller;
	private QuantitativeFact h;
	private QuantitativeFact l;
	private QuantitativeFact area;
	
	@Before
	public void setUp() throws Exception{
		controller = new DimensioneUlceraController();
		
		h = FactFactory.createQuantitative();
		Quantity quantityH = new Quantity();
		quantityH.setValue(new Double(7.5));
		h.setQuantity(quantityH);
		
		l = FactFactory.createQuantitative();
		Quantity quantityL = new Quantity();
		quantityL.setValue(new Double(6.2));
		l.setQuantity(quantityL);
		
		area = FactFactory.createQuantitative();
		Quantity quantityArea = new Quantity();
		quantityArea.setValue(new Double(0));
		area.setQuantity(quantityArea);
		
	}
	
	
	@Test
	public void retrieveArea(){
		Fact fact = FactFactory.createComposite();
		
		TypeSelector selector = mock(TypeSelector.class);
		when( selector.apply( fact ) ).thenReturn(area, h, l, area);
		
		Viewer viewer = mock(ViewerCustom.class);
 		ViewerLink vl = new SubViewer(UUID.randomUUID().toString());
 		vl.setSelector(selector);
 		when(viewer.getByPriority(anyLong())).thenReturn(vl);
 		
 		QuantitativeFact result = controller.retrieveArea( fact, viewer );
 		assertNotNull( result );
 		assertNotNull( result.getQuantity().getValue() );
 		assertEquals( new Double(46.5), result.getQuantity().getValue() );
	}
	
}
