package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("TABBED_PANEL")
public class TabbedPanel extends ViewerContainer{

	public TabbedPanel(String uuid){
		super(uuid);
	}
	protected TabbedPanel(){
		super();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/tabbedPanel.xhtml";
	}
	
	@Override 
	public boolean isValidSubViewer(ViewerLink sv){
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitTabbedPanel(this);
	}

}
