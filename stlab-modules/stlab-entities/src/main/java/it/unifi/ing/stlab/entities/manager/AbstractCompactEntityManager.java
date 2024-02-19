package it.unifi.ing.stlab.entities.manager;

import it.unifi.ing.stlab.entities.factory.AbstractCompactLinkFactory;
import it.unifi.ing.stlab.entities.model.compact.CompactEntity;
import it.unifi.ing.stlab.entities.model.compact.CompactLink;
import it.unifi.ing.stlab.entities.model.compact.UpdateLink;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.entities.model.traced.Actor;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

public abstract class AbstractCompactEntityManager 	
	<T extends CompactEntity<T,L,A>,
	 L extends CompactLink<T,L>,
	 A extends Action<T,A,U,H>, 
	 U extends Actor,
	 H extends Time> extends AbstractTracedEntityManager<T,A,U,H> {

	
	protected abstract AbstractCompactLinkFactory<T,L> getLinkFactory();

	public T modify( U author, H time, T source ) {
		if ( author == null || time == null || source == null )
			throw new IllegalArgumentException();
		
		if ( !source.isActive() )
			throw new IllegalArgumentException();
		
		T result = source.copy();
		result.init();
		getActionFactory().modifyAction(author, time, source, result);
		
		for ( L link : source.listActiveLinks() ) {
			T child = modify( author, time, link.getTarget());
			getLinkFactory().updateLink(result, child, link);
		}
		
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
		
		purgeChildren( entity );
		
		if ( entity.listChildren().size() > 0 ) 
			return entity;
		
		ModifyAction<T,A,U,H> modifyAction = ClassHelper.cast( entity.getOrigin(), ModifyAction.class );
		if ( !modifyAction.getSource().sameAs( modifyAction.getTarget() ))
			return entity;
		
		modifyAction.delete();
		entity.delete();
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void purgeChildren( T entity ) {
		for ( L link : entity.listActiveLinks() ) {
			if ( ClassHelper.instanceOf( link, UpdateLink.class )) {
				UpdateLink<T,L> updateLink = ClassHelper.cast( link, UpdateLink.class );
				
				T child = updateLink.getTarget();
				updateLink.assignTarget( null );
				T purged = purge( child );
	
				if ( purged != null ) {
					updateLink.assignTarget( purged );
				} else {
					updateLink.delete();
				}
			}
		}
	}
	
	public T clean( T entity ) {
		if ( entity == null || !entity.isActive() )
			throw new IllegalArgumentException();

		if ( entity.getDestination() != null ) 
			return entity;
		
		cleanChildren( entity );
		
		if ( !canClean( entity ))
			return entity;
		
		
		entity.getOrigin().delete();
		entity.delete();
		
		return null;
	}
	
	private boolean canClean( T entity ) {
		if (!entity.isEmpty() ) return false;

        return entity.listChildren().isEmpty();
		
//		for ( L link : entity.listChildren() ) {
//			if (!( ClassHelper.instanceOf( link, RemoveLink.class ))) {
//				return false;
//			}
//		}
    }
	
	@SuppressWarnings("unchecked")
	private void cleanChildren( T entity ) {
		for ( L link : entity.listActiveLinks() ) {
			
			T child = link.getTarget();
			link.assignTarget( null );
			T cleaned = clean( child );

			if ( cleaned != null ) {
				link.assignTarget( cleaned );

			} else {
				
				if(ClassHelper.instanceOf( link, UpdateLink.class )) {
					UpdateLink<T,L> updateLink = ClassHelper.cast( link, UpdateLink.class );
					T source = updateLink.getSource();
					L refersTo = updateLink.getRefersTo();
					getLinkFactory().removeLink( source, refersTo );
				}
				
				link.delete();
			}
			
		}
	}
	
//	@SuppressWarnings("unchecked")
//	private void cleanChildren( T entity ) {
//		for ( L link : entity.listActiveLinks() ) {
//			if ( ClassHelper.instanceOf( link, UpdateLink.class )) {
//				UpdateLink<T,L> updateLink = ClassHelper.cast( link, UpdateLink.class );
//				
//				T child = updateLink.getTarget();
//				updateLink.assignTarget( null );
//				T cleaned = clean( child );
//
//				if ( cleaned != null ) {
//					updateLink.assignTarget( cleaned );
//				} else {
//					T source = updateLink.getSource();
//					L refersTo = updateLink.getRefersTo();
//					updateLink.delete();
//					getLinkFactory().removeLink( source, refersTo );
//				}
//			}
//		}
//	}
	
}
