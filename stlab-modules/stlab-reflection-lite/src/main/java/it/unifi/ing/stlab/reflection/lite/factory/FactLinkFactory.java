package it.unifi.ing.stlab.reflection.lite.factory;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.model.facts.FactLite;
import it.unifi.ing.stlab.reflection.lite.model.facts.links.FactLinkLite;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class FactLinkFactory {

	public static FactLink insertLink(Fact source, Fact target) {
		//XXX
		FactLinkLite newLink = new FactLinkLite();
		newLink.assignSource( ClassHelper.cast( source, FactLite.class) );
		newLink.assignTarget( ClassHelper.cast( target, FactLite.class) );
		return newLink;
	}

}