package it.unifi.ing.stlab.empedocle.view.controllers.dermatology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class StadiazioneTController extends ContainerController {

	@Inject
	private PhenomenonDao phenomenonDao;
	
	public Fact retrieveStadiazione(Fact fact, Viewer viewer) {
		Fact stadiazione = findBySelector( fact, viewer.getByPriority( 3l ).getSelector() );
		calculateStadiazione( ClassHelper.cast( stadiazione, QualitativeFact.class ), fact, viewer );
		
		return stadiazione;
	}
	
	private void calculateStadiazione(QualitativeFact stadiazione, Fact fact, Viewer viewer) {
		Fact spessore = findBySelector( fact, viewer.getByPriority( 0l ).getSelector() );
		Fact ulcerazione = findBySelector( fact, viewer.getByPriority( 1l ).getSelector() );
		Fact mitosi = findBySelector( fact, viewer.getByPriority( 2l ).getSelector() );
		
		String classificazione = calculateClassificazione( ClassHelper.cast( spessore, QuantitativeFact.class ).getQuantity(), 
															ClassHelper.cast( ulcerazione, QualitativeFact.class ).getPhenomenon(), 
															ClassHelper.cast( mitosi, QuantitativeFact.class ).getQuantity() );
		
		if( classificazione == null || classificazione.isEmpty() ) return;
		
		Phenomenon p = phenomenonDao.findByName( stadiazione, classificazione, new Date() );
		if( p == null )
			throw new RuntimeException( "fenomeno con nome " + classificazione + " inesistente per il tipo " + stadiazione.getType().getName() );
		
		stadiazione.setPhenomenon( p );
	}
	
	private String calculateClassificazione(Quantity spessore, Phenomenon ulcerazione, Quantity mitosi) {
		if( spessore == null || spessore.getValue() == null ) return null;
		if ( ulcerazione == null ) return null;
		if( mitosi == null || mitosi.getValue() == null ) return null;
		
		StringBuilder result = new StringBuilder("T");
		boolean checkMitosi = false;
		
		if( spessore.getValue() <= 1.00d ) {
			result.append( "1 " );
			checkMitosi = true;
		} else if( spessore.getValue() > 1.00d && spessore.getValue() <= 2.00d ) {
			result.append( "2 " );
		} else if( spessore.getValue() > 2.00d && spessore.getValue() <= 4.00d ) {
			result.append( "3 " );
		} else {
			result.append( "4 " );
		}
		
		if( checkMitosi ) {
			if( "No".equals( ulcerazione.getName() ) && mitosi.getValue() < 1.00d )
				result.append( "A" );
			else if( "Si".equals( ulcerazione.getName() ) && mitosi.getValue() >= 1.00d )
				result.append( "B" );
			else
				return "Non Classificabile";
		} else {
			if( "No".equals( ulcerazione.getName() ) )
				result.append( "A" );
			else
				result.append( "B" );
		}
		
		return result.toString();
		
	}
	
}
