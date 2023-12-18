package it.unifi.stlab.view.model.widgets;

import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

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
	public boolean isValidSubViewer(ViewerLink sv){
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		return;
	}

}
