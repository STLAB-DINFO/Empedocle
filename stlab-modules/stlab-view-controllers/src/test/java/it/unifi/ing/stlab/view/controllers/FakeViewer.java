package it.unifi.ing.stlab.view.controllers;

import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;

public class FakeViewer extends Viewer {

	public FakeViewer(String uuid) {
		super(uuid);
	}
	protected FakeViewer() {
		super();
	}

	@Override
	public String getXhtml() {
		return null;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		return;
	}
}