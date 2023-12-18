package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class OutputListController {

	@Inject
	private FacesContext facesContext;
	
	public List<FactLink> getLinks(Fact fact, TypeSelector selector) {
		if(selector == null || selector.getTypeLink() == null){
			facesContext.addMessage( null, new FacesMessage("outputList necessita di un selettore"));
			return null;
		}
		
		Set<FactLink> filtered = selector.applyItem(fact);
		if(filtered == null || filtered.isEmpty()){
//			facesContext.addMessage( null, new FacesMessage("Nessun elemento trovato per la selezione "+ error(selector)));
			return null;
		}

		List<FactLink> result = new ArrayList<FactLink>();
		result.addAll( filtered );
		
		
		Collections.sort( result, new Comparator<FactLink>() {
			@Override
			public int compare(FactLink arg0, FactLink arg1) {
				int p0 = ( arg0.getPriority() == null ? 0 : arg0.getPriority().intValue() );
				int p1 = ( arg1.getPriority() == null ? 0 : arg1.getPriority().intValue() );
				return ( p0 - p1 );
			}
		});
		
		return result;
		
	}
			
//	private String error(TypeSelector selector) {
//		StringBuilder builder = new StringBuilder();
//
//		do {
//			builder.append("\"" + selector.getTypeLink().getName() + "\".");
//			selector = selector.getNext();
//		} while ( selector != null );
//		
//		return builder.toString();
//	}
}
