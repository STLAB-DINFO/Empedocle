package it.unifi.ing.stlab.reflection.impl.factory;

import it.unifi.ing.stlab.entities.factory.AbstractCompactLinkFactory;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactRemoveLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactUpdateLink;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

import java.util.UUID;

public class FactLinkFactory extends AbstractCompactLinkFactory<FactImpl, FactLinkImpl> {

	public FactLink insertLink(Fact source, Fact target) {
		//XXX
		return super.insertLink( 
							ClassHelper.cast(source, FactImpl.class), 
							ClassHelper.cast(target, FactImpl.class) );
	}
	
	@Override
	protected FactLinkImpl insertLink() {
		return new FactInsertLink( UUID.randomUUID().toString() );
	}

	@Override
	protected FactLinkImpl updateLink() {
		return new FactUpdateLink( UUID.randomUUID().toString() );
	}

	@Override
	protected FactLinkImpl removeLink() {
		return new FactRemoveLink( UUID.randomUUID().toString() );
	}

}