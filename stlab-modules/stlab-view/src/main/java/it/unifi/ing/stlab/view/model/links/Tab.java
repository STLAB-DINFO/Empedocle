package it.unifi.ing.stlab.view.model.links;

import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TAB")
public class Tab extends ViewerLink
		implements Persistable, CompositeLink<Viewer, ViewerLink>{

	private String label;

	public Tab(String uuid){
		super(uuid);
	}
	protected Tab(){
		super();
	}
	 
	// Label
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	@Override
	public void assignSource(Viewer newSource) {
		if ( !isValidSource( newSource )) 
			throw new IllegalArgumentException();
		
		super.assignSource(newSource);
	}
	
	private boolean isValidSource(Viewer newSource){
		return (newSource != null && ClassHelper.instanceOf( newSource, TabbedPanel.class ) && newSource.isValidSubViewer(this));
	}
	
}
