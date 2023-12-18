package it.unifi.ing.stlab.reflection.factory.types;

import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.UUID;

public class TypeLinkFactory {

	public static TypeLink createLink() {
		return new TypeLink( UUID.randomUUID().toString() );
	}
	
	public static TypeLink createLink(Type source, Type target) {
		TypeLink result = new TypeLink( UUID.randomUUID().toString() );
		result.assignSource(source);
		result.assignTarget(target);
		return result;
	}
	
}
