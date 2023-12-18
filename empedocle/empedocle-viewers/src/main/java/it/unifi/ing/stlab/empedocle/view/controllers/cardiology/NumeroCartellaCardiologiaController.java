package it.unifi.ing.stlab.empedocle.view.controllers.cardiology;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.view.controllers.ContainerController;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NumeroCartellaCardiologiaController extends ContainerController {

	@EJB
	private FolderNumberGenerator folderNumberGenerator;
	
	private QuantitativeFact numeroCartella;
	
	public QuantitativeFact retrieveNumeroCartella(Fact value, Viewer viewer){
		Fact f = findBySelector(value, viewer.getByPriority(0l).getSelector());
		numeroCartella = (f ==  null ? null : ClassHelper.cast(f, QuantitativeFact.class));
		
		if(numeroCartella != null && numeroCartella.getQuantity().getValue() == null) {
			folderNumberGenerator.generateFolderNumber(numeroCartella);
		}
		
		return numeroCartella;
		
	}

	public QuantitativeFact getNumeroCartella() {
		return numeroCartella;
	}
	
}
