package it.unifi.ing.stlab.entities.model.traced.actions;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;

public interface MergeAction
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time>
	
	extends Action<T,A,U,H> {

	public T getSource1();
	public void assignSource1( T tracedEntity );
	
	public T getSource2();
	public void assignSource2( T tracedEntity );
	
	public T getTarget();
	public void assignTarget( T tracedEntity );
	
}