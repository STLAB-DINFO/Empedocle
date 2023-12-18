package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;

import java.util.UUID;

public class ViewerUseFactory {

	public static ViewerUse createViewerUse() {
		return new ViewerUse( UUID.randomUUID().toString() );
	}
}
