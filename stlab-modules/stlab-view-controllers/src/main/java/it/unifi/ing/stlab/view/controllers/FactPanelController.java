package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.commons.qualifier.StatefulBean;
import it.unifi.ing.stlab.factquery.dao.FactQueryConstructor;
import it.unifi.ing.stlab.factquery.dao.FactQueryDao;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class FactPanelController {

	@Inject @StatefulBean
	private FactQueryDao factQueryDao;
	
	@Inject
	private Instance<FactQueryConstructor> queryConstructorInstance;
	

	public List<Fact> getFactRoots(FactPanel factPanel) {
		if( factPanel.getFactRoots() == null ) {
			factPanel.setFactRoots( factQueryDao.findFacts( factPanel.getQuery().getId(), 
											queryConstructorInstance.get() ) );
		}
		
		return factPanel.getFactRoots();
	}
	
	public Viewer getAssociatedViewer(FactPanel factPanel, Fact fact) {
		for( ViewerLink vl : factPanel.listChildrenOrdered() ) {
			if( fact.getType().equals( vl.getTarget().getType() ) )
				return vl.getTarget();
		}
		
		return null;
		
	}

}