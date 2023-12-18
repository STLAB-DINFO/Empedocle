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
import it.unifi.ing.stlab.view.model.links.TypeSelector;

@Named
@RequestScoped
public class CSDDController extends ContainerController {
	
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}
	
	// somma punteggio degli items
	public String retrieveOverallScore( Fact value ) {
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( FactLink link : value.listChildrenOrdered() ) {
			facts.add( link.getTarget() );
		}

		QuantitativeFact scoreFact = ClassHelper.cast( facts.remove( 5 ), QuantitativeFact.class ); // seleziona severity fact

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
	
	private int evaluateScore( List<Fact> facts ) {
		int score = 0;
		
		for ( Fact fact : facts ) {
			for( FactLink fl : fact.listChildrenOrdered() ) {
				score = score + extractScoreFromPhenomenon( fl.getTarget() );
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
