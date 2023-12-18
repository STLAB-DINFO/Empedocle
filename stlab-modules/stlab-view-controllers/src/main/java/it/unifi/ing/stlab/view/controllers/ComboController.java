package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ComboController {

	@Inject
	private PhenomenonDao phenomenonDao;
	
	@Inject
	private FacesContext facesContext;
	
	public boolean isSupported(Fact fact){
		if(fact == null){
			facesContext.addMessage( null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Nessuna osservazione da mostrare", ""));
			return false;
		}
		
		if(!isOsservazioneQualitativa(fact) && !isOsservazioneQuantitativa(fact)){
			facesContext.addMessage( null, 
					new FacesMessage( FacesMessage.SEVERITY_ERROR, 
							"Combo non supportata per l'osservazione di tipo " + fact.getType().getClass().getSimpleName(), ""));
			return false;
		}
		
		return true;
		
	}
	
	public boolean isOsservazioneQuantitativa(Fact f) {
		return f != null && ClassHelper.instanceOf( f, QuantitativeFact.class );
	}

	public boolean isOsservazioneQualitativa(Fact f) {
	return f != null && ClassHelper.instanceOf( f, QualitativeFact.class );
	}
	
	public List<SelectItem> getSelectItems(Fact fact){
		List<SelectItem> items = new ArrayList<SelectItem>();
		
		if(fact != null && ClassHelper.instanceOf( fact, QualitativeFact.class )) {
			List<Phenomenon> phenomena = phenomenonDao.findByFact((QualitativeFact)fact, new Date());
			Collections.sort(phenomena, new Comparator<Phenomenon>() {

				@Override
				public int compare(Phenomenon o1, Phenomenon o2) {
					if(o1.getPosition() != null && o2.getPosition() != null) {
						return o1.getPosition().compareTo(o2.getPosition());
					}
					else {
						return o1.getName().compareTo(o2.getName());
					}
				}
			});
			
			for(Phenomenon p : phenomena){
				items.add(new SelectItem(p, p.getName()));
			}
		}
		
		if(fact != null && ClassHelper.instanceOf( fact, QuantitativeFact.class )) {
			for( UnitUse uu : ClassHelper.cast( fact.getType(), QuantitativeType.class ).listUnits()){
				SelectItem s = new SelectItem(uu.getUnit(), uu.getUnit().getSimbol());
				s.setEscape(false);
				items.add(s);
			}
			
			Collections.sort(items, new Comparator<SelectItem>() {
				@Override
				public int compare(SelectItem o1, SelectItem o2) {
					return o1.getLabel().compareTo(o2.getLabel());
				}
			});
		}
		
		return items;
	}
	
}