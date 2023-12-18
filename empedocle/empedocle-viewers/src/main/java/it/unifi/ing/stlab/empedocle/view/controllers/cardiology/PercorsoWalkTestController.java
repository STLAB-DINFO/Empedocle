package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class PercorsoWalkTestController extends ContainerController{

	private QuantitativeFact numeroVolteCorridoio;
	private QuantitativeFact numeroTacche;
	private QuantitativeFact metriPercorsi;
	private QuantitativeFact numeroFermate;
	
	public QuantitativeFact retrieveNumeroVolteCorridoio(Fact value, Viewer viewer){
		try {
			Fact f = findBySelector(value, viewer.getByPriority(0l).getSelector());
			numeroVolteCorridoio = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		}
		catch(RuntimeException e){
//			facesContext.addMessage(null, new FacesMessage("La Vista Percorso Walk Test non può essere applicata a questo Tipo"));
		}
		return numeroVolteCorridoio;
	}
	
	
	public QuantitativeFact retrieveNumeroTacche(Fact value, Viewer viewer){
		try {
			Fact f = findBySelector(value, viewer.getByPriority(1l).getSelector());
			numeroTacche = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		}
		catch(RuntimeException e){
//			facesContext.addMessage(null, new FacesMessage("La Vista Percorso Walk Test non può essere applicata a questo Tipo"));
		}
		return numeroTacche;
	}
	
	
	public QuantitativeFact retrieveMetriPercorsi(Fact value, Viewer viewer){
		try {
			Fact f = findBySelector(value, viewer.getByPriority(2l).getSelector());
			metriPercorsi = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
			updateMetriPercorsi();
		}
		catch(RuntimeException e){
//			facesContext.addMessage(null, new FacesMessage("La Vista Percorso Walk Test non può essere applicata a questo Tipo"));
		}
		return metriPercorsi;
	}
	
	public QuantitativeFact retrieveNumeroFermate(Fact value, Viewer viewer){
		try {
			Fact f = findBySelector(value, viewer.getByPriority(3l).getSelector());
			numeroFermate = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		}
		catch(RuntimeException e){
//			facesContext.addMessage(null, new FacesMessage("La Vista Percorso Walk Test non può essere applicata a questo Tipo"));
		}
		return numeroFermate;
	}
	
	private void updateMetriPercorsi(){
		if(metriPercorsi == null)
			return;
		
		if(numeroVolteCorridoio == null || numeroVolteCorridoio.getQuantity().getValue() == null ||
				numeroTacche == null || numeroTacche.getQuantity().getValue() == null) {
			
			metriPercorsi.getQuantity().setValue( null );
			return;
			
		}
		
		metriPercorsi.getQuantity().setValue(round(1, (numeroVolteCorridoio.getQuantity().getValue()*37.5) + 
															(numeroTacche.getQuantity().getValue()*2.5)));
		
	}
	
	private Double round(int decimalPlace, Double x) {
		BigDecimal bd = new BigDecimal(x);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
	
	public QuantitativeFact getNumeroVolteCorridoio() {
		return numeroVolteCorridoio;
	}
	public void setNumeroVolteCorridoio(QuantitativeFact numeroVolteCorridoio) {
		this.numeroVolteCorridoio = numeroVolteCorridoio;
	}
	
	
	public QuantitativeFact getNumeroTacche() {
		return numeroTacche;
	}
	
	public QuantitativeFact getMetriPercorsi() {
		return metriPercorsi;
	}

	public QuantitativeFact getNumeroFermate() {
		return numeroFermate;
	}
	
}
