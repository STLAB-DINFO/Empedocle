package it.unifi.ing.stlab.empedocle.view.controllers.geriatrics;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

@Named
@RequestScoped
public class SigaretteController extends ContainerController {
	
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}

	// Indice Tabagico=(n sigarette/20)*AnniFumo
	public String retrieveOverallScore( Viewer viewer, Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}
		QuantitativeFact numberOfCig = ClassHelper.cast( facts.get(0), QuantitativeFact.class );
		QuantitativeFact years = ClassHelper.cast( facts.get(1), QuantitativeFact.class );
		QuantitativeFact scoreFact = ClassHelper.cast( facts.get( 2 ), QuantitativeFact.class );
		
		if( isEmpty(facts) || !isFactInizialized(numberOfCig) || !isFactInizialized(years) ) {
			scoreFact.getQuantity().setValue( null );
			return "--";
		} else {	
			double scoreValue = (numberOfCig.getQuantity().getValue() / 20)*years.getQuantity().getValue();
			scoreFact.getQuantity().setValue( (double) scoreValue );
	
			return String.valueOf( scoreValue );
		}
	}
		
	private boolean isEmpty( List<Fact> facts ) {
		
		for( Fact f : facts ) {
			if( f.isEmpty() ) {}
			else return false;
		}
		
		return true;
	}


	private Boolean isFactInizialized( Fact fact ) {
		QuantitativeFact qf = ClassHelper.cast( fact, QuantitativeFact.class );
		
		return ( qf == null || qf.isEmpty() || qf.getQuantity() == null || qf.getQuantity().getValue() == 0 ) ? false
				: true;
	}
}
