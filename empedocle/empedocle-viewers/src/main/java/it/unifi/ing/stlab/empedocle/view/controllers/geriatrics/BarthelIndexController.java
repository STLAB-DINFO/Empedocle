package it.unifi.ing.stlab.empedocle.view.controllers.geriatrics;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

@Named
@RequestScoped
public class BarthelIndexController extends ContainerController {
	
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}

	public String retrieveOverallScore( Viewer viewer, Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}
		
		QuantitativeFact scoreFact = ClassHelper.cast( facts.remove( 10 ), QuantitativeFact.class );
		
		if( isEmpty(facts) ) {
			scoreFact.getQuantity().setValue( null );
			return "--";
		} else {	
			int scoreValue = evaluateScore( facts );
			scoreFact.getQuantity().setValue( (double) scoreValue );
	
			return String.valueOf( scoreValue );
		}

	}

	private boolean isEmpty( List<Fact> facts ) {
		for( Fact f : facts ) {
			if( !f.isEmpty() )
				return false;
		}
		return true;
	}

	public String retrieveScoreClassification( Viewer viewer, Fact value ) {
		try {
			int score = Integer.parseInt( retrieveOverallScore( viewer, value ) ); 
			
			if( score <= 20 )
				return "dipendenza totale";
			else if ( score <= 60 ) 
				return "dipendenza severa";
			else if ( score <= 90 )
				return "dipendenza moderata";
			else if ( score <= 99 )
				return "dipendenza lieve";
			else if ( score == 100 )
				return "indipendente";
		} catch ( NumberFormatException e ) {
			return "--";
		}
		return "--";
	}

	private int evaluateScore( List<Fact> facts ) {
		int score = 0;
		for ( Fact fact : facts ) {
			score = score + extractScoreFromPhenomenon( fact );
		}

		return score;
	}

	private int extractScoreFromPhenomenon( Fact fact ) {
		QualitativeFact qf = ClassHelper.cast( fact, QualitativeFact.class );
		
		return ( qf == null || qf.getPhenomenon() == null ) ? 0
				: Integer.parseInt( qf.getPhenomenon().getName().split( "-" )[0].trim() );
	}
}
