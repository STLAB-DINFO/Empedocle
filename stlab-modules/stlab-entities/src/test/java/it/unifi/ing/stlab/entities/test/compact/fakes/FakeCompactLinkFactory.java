package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.factory.AbstractCompactLinkFactory;

public class FakeCompactLinkFactory extends AbstractCompactLinkFactory<FakeCompactEntity, FakeCompactLink>{

	@Override
	protected FakeCompactLink insertLink() {
		return new FakeInsertLink();
	}

	@Override
	protected FakeCompactLink updateLink() {
		return new FakeUpdateLink();
	}

	@Override
	protected FakeCompactLink removeLink() {
		return new FakeRemoveLink();
	}

}
