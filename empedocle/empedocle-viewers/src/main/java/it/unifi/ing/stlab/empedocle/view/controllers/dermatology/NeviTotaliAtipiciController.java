package it.unifi.ing.stlab.empedocle.view.controllers.dermatology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NeviTotaliAtipiciController extends ContainerController {

	public boolean isAMS(Fact value, Viewer viewer) {
		QualitativeFact neviTotali = ClassHelper.cast( 
								findBySelector( value, viewer.getByPriority(0l).getSelector() ),
								QualitativeFact.class );
		
		QualitativeFact neviAtipici = ClassHelper.cast( 
								findBySelector( value, viewer.getByPriority(1l).getSelector() ),
								QualitativeFact.class );
		
		if( neviTotali.getPhenomenon() == null || neviAtipici.getPhenomenon() == null ) {
			clearPhenomenon(value, viewer);
			return false;
		}
		
		if( ! ">100".equals( neviTotali.getPhenomenon().getName() ) ||
					"0".equals( neviAtipici.getPhenomenon().getName() ) ) {
			clearPhenomenon(value, viewer);
			return false;
		}
		
		setPhenomenon(value, viewer);
		return true;
	}
	
	private void clearPhenomenon(Fact value, Viewer viewer) {
		QualitativeFact atm = ClassHelper.cast( 
				findBySelector( value, viewer.getByPriority(2l).getSelector() ),
				QualitativeFact.class );
		
		atm.setPhenomenon( null );
	}
	
	// questo metodo funziona posto che l'osservazione abbia solo un fenomeno selezionabile
	private void setPhenomenon(Fact value, Viewer viewer) {
		QualitativeFact atm = ClassHelper.cast( 
				findBySelector( value, viewer.getByPriority(2l).getSelector() ),
				QualitativeFact.class );
		
		EnumeratedType type = ClassHelper.cast( atm.getType(), EnumeratedType.class );
		
		atm.setPhenomenon( type.listPhenomena().iterator().next() );
	}
	
}
