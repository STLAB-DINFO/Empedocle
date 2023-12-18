package it.unifi.ing.stlab.empedocle.view.controllers.dermatology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class VisitaClinicaEsitoController extends ContainerController {

	private QualitativeFact esito;
	private TextualFact note;
	
	public QualitativeFact retrieveEsito(Fact value, Viewer viewer) {
		if( esito == null ) {
			Fact f = findBySelector( value, viewer.getByPriority( 0l ).getSelector() );
			esito = ( f ==  null ? null : ClassHelper.cast( f, QualitativeFact.class ) );
		}
		
		return esito;
	}
	
	public QualitativeFact getEsito() {
		return esito;
	}
	
	public TextualFact retrieveNote(Fact value, Viewer viewer) {
		if( note == null ) {
			Fact f = findBySelector( value, viewer.getByPriority( 1l ).getSelector() );
			note = ( f ==  null ? null : ClassHelper.cast( f, TextualFact.class ) );
			updateNote( esito.getPhenomenon(), note );
		}
		
		return note;
	}
	
	public TextualFact getNote() {
		return note;
	}
	
	private void updateNote(Phenomenon esito, TextualFact note) {
		note.setText( null );
		
		if( esito == null ) {
			return;
		}
		
		if( "Autocontrollo".equals( esito.getName() ) ||
				"Controllo dopo 24 mesi".equals( esito.getName() ) ) {
			note.setText( getTextAutocontrollo() );
		}
		
		if( "Controllo dopo 6 mesi".equals( esito.getName() ) ||
				"Controllo dopo 12 mesi".equals( esito.getName() ) ) {
			note.setText( getTextSeiDodiciMesi( esito.getName() ) );
		}
	}
	
	private String getTextAutocontrollo() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Non si rileva la presenza di neoformazioni pigmentate sospette nelle aree cutanee esaminate." );
		sb.append( "Assenza di importanti fattori di rischio per neoplasie cutanee.\n" );
		sb.append( "Si consiglia l'esecuzione di un autocontrollo periodico (circa ogni 3 mesi) della propria pelle." );
		sb.append( "Per osservare le aree corporee scarsamente esplorabili (dorso, superficie posteriore, gambe e cosce) " );
		sb.append( "E' consigliabile l'uso di uno specchio.\n" );
		sb.append( "Nel caso che venga osservato un neo \"dubbio\" " );
		sb.append( "cioè un neo che sta cambiando dimensione, forma e\\o colore, rivolgersi tempestivamente al medico curante " );
		sb.append( "che, se necessario, consiglierà una visita dermatologica specialistica.\n" );

		return sb.toString();
	}
	
	private String getTextSeiDodiciMesi(String tempo) {
		StringBuilder sb = new StringBuilder();
		sb.append( "Non si rileva la presenza di neoformazioni pigmentate sospette nelle aree cutanee esaminate.\n" );
		sb.append( "Si consiglia tuttavia un " );
		sb.append( tempo.toLowerCase() );
		sb.append( ".\n" );
		sb.append( "Ricordarsi di eseguire un autocontrollo periodico (circa ogni 3 mesi) della propria pelle." );
		sb.append( "Se prima del controllo viene osservato un neo \"sospetto\", " );
		sb.append( "cioè che sta cambiando dimensione, forma e\\o colore, rivolgersi tempestivamente al medico curante.\n" );
		
		return sb.toString();
	}
	
	
}
