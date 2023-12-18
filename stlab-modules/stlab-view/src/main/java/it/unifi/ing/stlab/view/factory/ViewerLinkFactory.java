package it.unifi.ing.stlab.view.factory;

import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.util.UUID;

public class ViewerLinkFactory {

	public static ViewerLink createSubViewer() {
		return new SubViewer( UUID.randomUUID().toString() );
	}
	
	public static ViewerLink createTab() {
		return new Tab( UUID.randomUUID().toString() );
	}
}
