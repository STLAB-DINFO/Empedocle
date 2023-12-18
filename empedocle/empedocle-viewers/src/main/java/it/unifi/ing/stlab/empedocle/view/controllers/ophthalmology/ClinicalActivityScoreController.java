package it.unifi.ing.stlab.empedocle.view.controllers.ophthalmology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ClinicalActivityScoreController extends ContainerController {

	public String retrieveScore(Viewer viewer, Fact value) {
		List<Fact> facts = new LinkedList<Fact>();
		for( ViewerLink link : viewer.listChildrenOrdered() ) {
			facts.add( findBySelector( value, link.getSelector() ) );
		}
		
		TextualFact score = ClassHelper.cast( facts.remove( 7 ), TextualFact.class );
		String scoreText = evaluateScore( facts );
		
		score.setText( scoreText );
		
		return scoreText;
	}
	
	private String evaluateScore(List<Fact> facts) {
		int score = 0;
		String base = "/7";
		for( Fact fact : facts ) {
			if( "Si".equals( extractPhenomenonName( fact ) ) ){
				score++;
			}
		}
		
		return score + base;
	}
	
	private String extractPhenomenonName(Fact fact) {
		QualitativeFact qf = ClassHelper.cast( fact, QualitativeFact.class );
		return (qf == null || qf.getPhenomenon() == null) ? "" : qf.getPhenomenon().getName();
	}
}
