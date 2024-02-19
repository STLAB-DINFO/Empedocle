package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("GRID")
public class Grid extends ViewerContainer {
	
	private PanelOrientation orientation;

	public Grid(String uuid) {
		super(uuid);
	}
	protected Grid() {
		super();
	}
	
	public PanelOrientation getOrientation() {
		return orientation;
	}
	public void setOrientation(PanelOrientation orientation) {
		this.orientation = orientation;
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/grid.xhtml";
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {

        return sv != null && ClassHelper.instanceOf(sv, SubViewer.class);
    }
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitGrid(this);
	}

}
