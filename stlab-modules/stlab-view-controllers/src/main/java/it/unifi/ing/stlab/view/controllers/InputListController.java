package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.model.links.TypeSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class InputListController {
	
	@Inject
	private FacesContext facesContext;
	
	public List<FactLink> getLinks(Fact fact, TypeSelector selector) {
		if(selector == null || selector.getTypeLink() == null){
			facesContext.addMessage( null, new FacesMessage("inputList necessita di un selettore") );
			return null;
		}
		
		Set<FactLink> filtered = selector.applyItem(fact);
		if(filtered == null || filtered.isEmpty()){
//			facesContext.addMessage( null, new FacesMessage("Nessun elemento trovato per la selezione " + error(selector)) );
			return new LinkedList<FactLink>();
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
	
	public boolean canAddFact(Fact fact, TypeSelector selector){
		if(fact == null || selector == null)
			return false;
		
		TypeLink typeLink = selector.applyType(fact.getType());
		
		if(typeLink == null)
			return false;
		
		return (typeLink.getMax() == 0 || (typeLink.getMax() > 1 && typeLink.getMax() > getLinks(fact, selector).size()));
		
	}
	
	public TypeLink getTypeLink(CompositeFact fact, TypeSelector selector) {
		return selector.applyType(fact.getType());
	}
	public Fact getFact(CompositeFact fact, TypeSelector selector) {
		return getLinks(fact, selector).get(0).getSource();
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