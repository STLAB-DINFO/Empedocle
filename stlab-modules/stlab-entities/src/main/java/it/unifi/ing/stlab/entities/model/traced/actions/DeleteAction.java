package it.unifi.ing.stlab.entities.model.traced.actions;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;

public interface DeleteAction
	<T extends TracedEntity<T,A>, 
	 A extends Action<T,A,U,H>,
	 U extends Actor,
	 H extends Time>
	
	extends Action<T,A,U,H> {

	public T getSource();
	public void assignSource( T tracedEntity );
	
}