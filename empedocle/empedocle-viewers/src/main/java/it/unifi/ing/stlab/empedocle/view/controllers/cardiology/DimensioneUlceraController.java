package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class DimensioneUlceraController extends ContainerController {

	public QuantitativeFact retrieveH(Fact value, Viewer viewer) {
		QuantitativeFact h = retrieveFact( value, viewer, 0l );
		return h;
	}
	
	
	public QuantitativeFact retrieveL(Fact value, Viewer viewer) {
		QuantitativeFact l = retrieveFact( value, viewer, 1l );
		return l;
	}
	
	
	public QuantitativeFact retrieveArea(Fact value, Viewer viewer) {
		QuantitativeFact area = retrieveFact( value, viewer, 2l );
		if( area != null )
			updateArea(value, viewer);
		
		return area;
	}
	
	
	private QuantitativeFact retrieveFact(Fact value, Viewer viewer, Long priority){
		QuantitativeFact result = null;
		
		try {
			Fact f = findBySelector( value, viewer.getByPriority( priority ).getSelector() );
			result = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		}
		catch(RuntimeException e){
			return null;
		}
		
		return result;
	}
	
	
	private void updateArea(Fact value, Viewer viewer){
		QuantitativeFact h = retrieveFact( value, viewer, 0l );
		QuantitativeFact l = retrieveFact( value, viewer, 1l );
		QuantitativeFact area = retrieveFact( value, viewer, 2l );
		
		if( h == null || h.getQuantity().getValue() == null ) {
			area.getQuantity().setValue( null );
			return;
		}
		
		if( l == null || l.getQuantity().getValue() == null ) {
			area.getQuantity().setValue( null );
			return;
		}
		
		area.getQuantity().setValue( round( 1, h.getQuantity().getValue()*l.getQuantity().getValue() ) );
		
	}
	
	
	private Double round(int decimalPlace, Double x) {
		BigDecimal bd = new BigDecimal(x);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
	
}
