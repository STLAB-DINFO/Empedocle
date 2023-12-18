package it.unifi.ing.stlab.entities.manager;

import it.unifi.ing.stlab.entities.factory.AbstractActionFactory;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

public abstract class AbstractTracedEntityManager 	
	<T extends TracedEntity<T,A>,
	 A extends Action<T,A,U,H>, 
	 U extends Actor,
	 H extends Time> {

	
	protected abstract AbstractActionFactory<T,A,U,H> getActionFactory();

	public T modify( U author, H time, T source ) {
		if ( author == null || time == null || source == null )
			throw new IllegalArgumentException();
		
		if ( !source.isActive() )
			throw new IllegalArgumentException();
		
		T result = source.copy();
		result.init();
		getActionFactory().modifyAction(author, time, source, result);
		
		return result;
	}
	
	public T delete( U author, H time, T source ) {
		if ( author == null || time == null || source == null )
			throw new IllegalArgumentException();
		
		if ( !source.isActive() )
			throw new IllegalArgumentException();
		
		getActionFactory().deleteAction(author, time, source);
		
		return source;
	}
	
	@SuppressWarnings("unchecked")
	public T purge( T entity ) {
		if ( entity == null || !entity.isActive() )
			throw new IllegalArgumentException();
		
		if ( entity.getOrigin() == null || !( ClassHelper.instanceOf( entity.getOrigin(), ModifyAction.class )))
			return entity;
		
		ModifyAction<T,A,U,H> modifyAction = ClassHelper.cast( entity.getOrigin(), ModifyAction.class );
		if ( !modifyAction.getSource().sameAs( modifyAction.getTarget() ))
			return entity;
		
		modifyAction.delete();
		entity.delete();
		
		return null;
	}
	
	public T clean( T entity ) {
		if ( entity == null || !entity.isActive() )
			throw new IllegalArgumentException();

		if ( entity.getDestination() != null ) 
			return entity;
		
		if ( !entity.isEmpty() )
			return entity;
		
		entity.getOrigin().delete();
		entity.delete();
		
		return null;
	}
	
	protected <S extends T> S init( S entity, U author, H time ) {
		entity.init();
		getActionFactory().createAction( author, time, entity );
		return entity;
	}
	
	
}
