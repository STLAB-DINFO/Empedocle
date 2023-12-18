package it.unifi.ing.stlab.view.model.widgets;

import it.unifi.ing.stlab.view.model.Viewer;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ViewerOutput extends Viewer {

	public ViewerOutput(String uuid) {
		super(uuid);
	}

	protected ViewerOutput() {
		super();
	}
}