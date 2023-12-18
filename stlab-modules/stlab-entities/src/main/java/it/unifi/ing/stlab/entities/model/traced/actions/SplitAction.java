package it.unifi.ing.stlab.entities.model.traced.actions;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;

public interface SplitAction
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time>
	
	extends Action<T,A,U,H> {

	public T getSource();
	public void assignSource( T tracedEntity );
	
	public T getTarget1();
	public void assignTarget1( T tracedEntity );

	public T getTarget2();
	public void assignTarget2( T tracedEntity );
	
}