package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;

public abstract class ContainerController {

	public Fact findBySelector(Fact fact, TypeSelector selector) {
		if ( selector == null ) return fact;
		
		Fact result = selector.apply( fact );
		
		return result;
	}
	
	public boolean isVistaCompositaItem(Viewer viewer){
		return viewer != null && (
				ClassHelper.instanceOf( viewer, InputList.class ) || 
				ClassHelper.instanceOf( viewer, OutputList.class ) || 
				ClassHelper.instanceOf( viewer, OutputPath.class ));
	}
	
	public boolean show(ViewerContainer container, Fact fact){
		if(!container.getCollapse())
			return true;
		
		return (fact != null && !fact.isEmpty());
	}
	
}
