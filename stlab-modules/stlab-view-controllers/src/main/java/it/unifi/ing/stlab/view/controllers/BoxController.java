package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.container.Box;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BoxController extends ContainerController{

	//TODO testare
	@Override
	public boolean show(ViewerContainer container, Fact fact){
		if(!container.getCollapse())
			return true;
		
		Fact content = null;
		
		if(((Box)container).getSize() == 1) {
			content = findBySelector( fact, container.getByPriority( 0l ).getSelector() );
			
		} else if(((Box)container).getSize() > 1) {
			content = findBySelector( fact, container.getByPriority( 1l ).getSelector() );
			
		}
		
		return ( content != null ? !content.isEmpty() : false );
	}
	
}