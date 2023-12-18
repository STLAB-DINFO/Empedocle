package it.unifi.ing.stlab.empedocle.view.controllers.geriatrics;

import java.util.HashMap;
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
public class MMSEController extends ContainerController {
		
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}

	// somma degli 11 items
	public String retrieveOverallScore( Viewer viewer, Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}

		facts.remove( 14 );  // rimuovo Punteggio complessivo aggiustato dalla lista dei facts su cui ciclare
		QuantitativeFact scoreFact = ClassHelper.cast( facts.remove( 13 ), QuantitativeFact.class );  // estraggo Punteggio complessivo
		facts.remove( 12 ); // rimuovo 
		facts.remove( 11 ); // rimuovo 
		
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
	
	public String retrieveAdjustedOverallScore( Viewer viewer, Fact value ) {
		if( value.isEmpty() )
			return "--";		
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}

		QuantitativeFact scoreFact = ClassHelper.cast( facts.remove( 14 ), QuantitativeFact.class );  // estraggo Punteggio complessivo aggiustato
		facts.remove( 13 ); // rimuovo Punteggio complessivo
		QualitativeFact ageIntervalFact = ClassHelper.cast( facts.remove( 12 ), QualitativeFact.class ); // rimuovo Intervallo di età
		QualitativeFact educationLevelFact = ClassHelper.cast( facts.remove( 11 ), QualitativeFact.class) ; // rimuovo Livello di educazione
		
		if( isEmpty(facts) ) {
			scoreFact.getQuantity().setValue( null );
			
			return "--";
		} else {	
			if( !ageIntervalFact.isEmpty() && !educationLevelFact.isEmpty() ) {
				int scoreValue = evaluateScore( facts );
				double adjustedValue = scoreValue + getCorrectionFactor( 
						ageIntervalFact.getPhenomenon().getName() + " " + educationLevelFact.getPhenomenon().getName() );
				scoreFact.getQuantity().setValue( (double) adjustedValue );
				
				return String.valueOf( adjustedValue );
			} else {
				scoreFact.getQuantity().setValue( null );
				
				return "--";
			}
		}
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
	
	private double getCorrectionFactor( String key ) {
		HashMap<String, Double> correctionFactorMap = new HashMap<String, Double>();
		correctionFactorMap.put( "65-69 0-4 anni", 0.4 );
		correctionFactorMap.put( "65-69 5-7 anni", 1.1 );
		correctionFactorMap.put( "65-69 8-12 anni", -2.0 );
		correctionFactorMap.put( "65-69 13-17 anni", -2.8 );
		
		correctionFactorMap.put( "70-74 0-4 anni", 0.7 );
		correctionFactorMap.put( "70-74 5-7 anni", -0.7 );
		correctionFactorMap.put( "70-74 8-12 anni", -1.6 );
		correctionFactorMap.put( "70-74 13-17 anni", -2.3 );
		
		correctionFactorMap.put( "75-79 0-4 anni", 1.0 );
		correctionFactorMap.put( "75-79 5-7 anni", -0.3 );
		correctionFactorMap.put( "75-79 8-12 anni", -1.0 );
		correctionFactorMap.put( "75-79 13-17 anni", -1.7 );
		
		correctionFactorMap.put( "80-84 0-4 anni", 1.5 );
		correctionFactorMap.put( "80-84 5-7 anni", 0.4 );
		correctionFactorMap.put( "80-84 8-12 anni", -0.3 );
		correctionFactorMap.put( "80-84 13-17 anni", -0.9 );
		
		correctionFactorMap.put( "85-89 0-4 anni", 2.2 );
		correctionFactorMap.put( "85-89 5-7 anni", 1.4 );
		correctionFactorMap.put( "85-89 8-12 anni", 0.8 );
		correctionFactorMap.put( "85-89 13-17 anni", 0.3 );
		
		return correctionFactorMap.get( key );
	}
}
