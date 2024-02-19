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
public class ADLController extends ContainerController {
	
	public FactLink findFactLinkBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return null;
		
		FactLink result = selector.applyItem( fact ).iterator().next();
		
		return result;
	}

	// Assignment of one point for each option that starts with an 'a'
	// (which determines the dependency value, up to a maximum of six)
	// and for each option that starts with a 'b'
	// (which determines the independence value, up to a maximum of six)
	public String retrieveOverallScore( Viewer viewer, Fact value, String scoreType) {
		int position;
		if(scoreType.equals("a")){
			position = 6;
		}
		else{
			position = 7;
		}
		
		if( value.isEmpty() )
			return "--";
		
		List<Fact> facts = new LinkedList<Fact>();
		for ( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}
		
		QuantitativeFact scoreFact = ClassHelper.cast( facts.remove( position ), QuantitativeFact.class );
		
		if( isEmpty(facts) ) {
			scoreFact.getQuantity().setValue( null );
			return "--";
		} else {	
			int scoreValue = evaluateScore( facts, scoreType );
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

	private int evaluateScore( List<Fact> facts, String scoreType ) {
		int score = 0;
		for ( Fact fact : facts ) {
			score = score + extractScoreFromPhenomenon( fact, scoreType );
		}

		return score;
	}

	private int extractScoreFromPhenomenon( Fact fact, String scoreType ) {
		try{
			QualitativeFact qf = ClassHelper.cast( fact, QualitativeFact.class );
			
			if( qf == null || qf.getPhenomenon() == null || qf.getPhenomenon().getName().contains( "N/A" ) )
				return 0;
			else{
				if( qf.getPhenomenon().getName().split( "-" )[0].trim().equals( scoreType ))
					return 1;
			}
			return 0;
		}
		catch (ClassCastException e) {
			return 0;
		}
	}
}
