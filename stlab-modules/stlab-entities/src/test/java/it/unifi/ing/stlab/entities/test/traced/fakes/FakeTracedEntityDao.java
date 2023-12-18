package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.manager.AbstractTracedEntityManager;

public class FakeTracedEntityDao 
	extends AbstractTracedEntityManager<FakeTracedEntity,FakeAction,FakeActor,FakeTime> {

	@Override
	protected AbstractActionFactory<FakeTracedEntity,FakeAction,FakeActor,FakeTime> getActionFactory() {
		return new FakeActionFactory();
	}
	
	public FakeTracedEntity create( FakeActor author, FakeTime time ) {
		if ( author == null || time == null )
			throw new IllegalArgumentException();
		
		FakeTracedEntity result = new FakeTracedEntity();
		result.init();
		getActionFactory().createAction( author, time, result );
		return result;
	}
}