package it.unifi.ing.stlab.view.factory;

import java.util.UUID;

import it.unifi.ing.stlab.view.model.links.TypeSelector;

public class TypeSelectorFactory {
	
	public static TypeSelector createSelector() {
		return new TypeSelector( UUID.randomUUID().toString() );
	}

}
