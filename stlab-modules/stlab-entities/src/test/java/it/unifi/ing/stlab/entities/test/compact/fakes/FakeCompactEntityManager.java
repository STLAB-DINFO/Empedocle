package it.unifi.ing.stlab.entities.test.compact.fakes;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.factory.AbstractCompactLinkFactory;
import it.unifi.ing.stlab.entities.manager.AbstractCompactEntityManager;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeActor;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTime;

public class FakeCompactEntityManager 
	extends AbstractCompactEntityManager<FakeCompactEntity, FakeCompactLink, FakeCompactAction, FakeActor, FakeTime> {

	@Override
	protected AbstractActionFactory<FakeCompactEntity, FakeCompactAction, FakeActor, FakeTime> getActionFactory() {
		return new FakeCompactActionFactory();
	}
	
	@Override
	protected AbstractCompactLinkFactory<FakeCompactEntity, FakeCompactLink> getLinkFactory() {
		return new FakeCompactLinkFactory();
	}

	public FakeCompactEntity create( FakeActor author, FakeTime time ) {
		if ( author == null || time == null )
			throw new IllegalArgumentException();
		
		FakeCompactEntity result = new FakeCompactEntity();
		result.init();
		getActionFactory().createAction( author, time, result );
		return result;
	}
}