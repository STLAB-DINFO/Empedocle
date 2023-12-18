package it.unifi.ing.stlab.reflection.impl.manager;


import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.factory.AbstractCompactLinkFactory;
import it.unifi.ing.stlab.entities.manager.AbstractCompactEntityManager;
import it.unifi.ing.stlab.reflection.impl.factory.FactActionFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QualitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QuantitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TemporalFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.actions.FactAction;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

public class FactManager 
	extends AbstractCompactEntityManager<FactImpl, FactLinkImpl, FactAction, User, Time> {

	@Override
	protected AbstractActionFactory<FactImpl, FactAction, User, Time> getActionFactory() {
		return new FactActionFactory();
	}

	@Override
	protected AbstractCompactLinkFactory<FactImpl, FactLinkImpl> getLinkFactory() {
		return new FactLinkFactory();
	}
	
	public TextualFactImpl createTextual( User author, Time time ) {
		return init( FactFactory.createTextual(), author, time );
	}

	public QuantitativeFactImpl createQuantitative( User author, Time time ) {
		return init( FactFactory.createQuantitative(), author, time );
	}

	public QualitativeFactImpl createQualitative( User author, Time time ) {
		return init( FactFactory.createQualitative(), author, time );
	}
	
	public TemporalFactImpl createTemporal( User author, Time time ) {
		return init( FactFactory.createTemporal(), author, time );
	}

	public CompositeFactImpl createComposite( User author, Time time ) {
		return init( FactFactory.createComposite(), author, time );
	}
	
	
	
}
