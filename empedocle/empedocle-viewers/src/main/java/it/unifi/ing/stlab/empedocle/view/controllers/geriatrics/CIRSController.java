package it.unifi.ing.stlab.empedocle.view.controllers.geriatrics;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import it.unifi.ing.stlab.view.model.links.TypeSelector;

@Named
@RequestScoped
public class CIRSController extends ContainerController {
	
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}

	// the severity index, which is the result of the average scores of the first 13 categories (excluding psychiatric and behavioral problems);
	// the maximum score achievable is five;
	public String retrieveSeverityIndex( Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( FactLink link : value.listChildrenOrdered() ) {
			facts.add( link.getTarget() );
		}

		facts.remove( 15 ); // removes comorbidity fact
		QuantitativeFact severityFact = ClassHelper.cast( facts.remove( 14 ), QuantitativeFact.class ); // selects severity fact
		facts.remove( 13 );  // removes the fact related to psychiatric and behavioral problems

		if( isEmpty(facts) ) {
			severityFact.getQuantity().setValue( null );
			return "--";
		} else {	
			double score = evaluateSeverityScore( facts );
			severityFact.getQuantity().setValue( score );
	
			return String.valueOf( score );
		}
	}
	
	private boolean isEmpty( List<Fact> facts ) {
		for( Fact f : facts ) {
			if( !f.listChildrenOrdered().get( 0 ).getTarget().isEmpty() )
				return false;
		}
		return true;
	}

	// the comorbidity index, which represents the number of categories in which a score of 3 or higher is obtained
	// (also in this case referring only to the first 13 categories; the maximum score achievable is 13).

	public String retrieveComorbidityIndex( Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( FactLink link : value.listChildrenOrdered() ) {
			facts.add( link.getTarget() );
		}

		QuantitativeFact comorbidityFact = ClassHelper.cast( facts.remove( 15 ), QuantitativeFact.class ); // selects comorbidity fact
		facts.remove( 14 ); // removes severity fact
		facts.remove( 13 ); // removes the fact related to psychiatric and behavioral problems

		if( isEmpty(facts) ) {
			comorbidityFact.getQuantity().setValue( null );
			return "--";
		} else {	
			int score = evaluateComorbidityScore( facts );
			comorbidityFact.getQuantity().setValue( (double) score );
	
			return String.valueOf( score );
		}
	}	

	private double evaluateSeverityScore( List<Fact> facts ) {
		double score = 0;
		for ( Fact fact : facts ) {
			score = score + extractScoreFromPhenomenon( fact.listChildrenOrdered().get( 0 ).getTarget() );
		}

		return new BigDecimal( score/(facts.size()) ).setScale( 2, RoundingMode.HALF_UP ).doubleValue();
	}
	
	private int evaluateComorbidityScore( List<Fact> facts ) {
		int score = 0;
		for ( Fact fact : facts ) {
			if ( extractScoreFromPhenomenon( fact.listChildrenOrdered().get( 0 ).getTarget() ) >= 3 ) {
				score++;
			}
		}

		return score;
	}

	private int extractScoreFromPhenomenon( Fact fact ) {
		QualitativeFact qf = ClassHelper.cast( fact, QualitativeFact.class );
		
		return ( qf == null || qf.getPhenomenon() == null ) ? 0
				: Integer.parseInt( qf.getPhenomenon().getName().split( "-" )[0].trim() );
	}
}
