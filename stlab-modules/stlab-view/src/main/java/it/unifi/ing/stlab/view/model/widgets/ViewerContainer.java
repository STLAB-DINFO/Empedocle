package it.unifi.ing.stlab.view.model.widgets;

import it.unifi.ing.stlab.view.model.Viewer;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ViewerContainer extends Viewer {

	private Boolean collapse;
	
	public ViewerContainer(String uuid) {
		super(uuid);
	}
	protected ViewerContainer() {
		super();
	}
	
	public Boolean getCollapse() {
		return collapse;
	}
	public void setCollapse(Boolean collapse) {
		this.collapse = collapse;
	}
	
	
}