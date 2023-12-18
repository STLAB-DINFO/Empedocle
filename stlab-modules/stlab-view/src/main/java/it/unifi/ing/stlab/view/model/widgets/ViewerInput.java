package it.unifi.ing.stlab.view.model.widgets;

import it.unifi.ing.stlab.view.model.Viewer;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ViewerInput extends Viewer {

	private Boolean required;

	public ViewerInput(String uuid) {
		super(uuid);
	}
	protected ViewerInput() {
		super();
	}

	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
}