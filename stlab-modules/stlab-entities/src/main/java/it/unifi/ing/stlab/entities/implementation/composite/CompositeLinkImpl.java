package it.unifi.ing.stlab.entities.implementation.composite;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.model.Delegated;
import it.unifi.ing.stlab.entities.model.composite.CompositeEntity;
import it.unifi.ing.stlab.entities.model.composite.CompositeLink;
import it.unifi.ing.stlab.entities.utils.MethodHelper;

import java.lang.reflect.Method;
import java.util.Set;


public class CompositeLinkImpl
	<T extends CompositeEntity<T,L>, 
	 L extends CompositeLink<T,L>> 

	implements CompositeLink<T,L>, Delegated<L> {

	
	private L delegator;
	private T source;
	private T target;
	private Long priority;
	
	public CompositeLinkImpl() {
		super();
	}

	
	@Override
	public L getDelegator() {
		return delegator;
	}
	@Override
	public void setDelegator(L delegator) {
		this.delegator = delegator;
	}

	
	@Override
	public void delete() {
		assignSource( null );
		assignTarget( null );
		
		GarbageCollector.getInstance().garbage( getDelegator() );
	}


	// Source methods
	@Override
	public T getSource() {
		return source;
	}
	public void setSource(T source) {
		this.source = source;
	}
	@Override
	public void assignSource(T newSource ) {
		if ( isCurrentSource( newSource )) return;
		if ( !isValidSource( newSource )) throw new IllegalArgumentException();

		cleanDescendents();
		cleanAncestors();
		
		cleanChildren();
		source = newSource;
		updateChildren();
		
		updateDescendents();
		updateAncestors();
	}
	
	
	// Target methods
	@Override
	public T getTarget() {
		return target;
	}
	public void setTarget(T target) {
		this.target = target;
	}
	@Override
	public void assignTarget(T newTarget) {
		if ( isCurrentTarget( newTarget )) return;
		if ( !isValidTarget( newTarget )) throw new IllegalArgumentException();

		cleanDescendents();
		cleanAncestors();
		
		cleanParents();
		target = newTarget;
		updateParents();
		
		updateDescendents();
		updateAncestors();
	}


	@Override
	public Long getPriority() {
		return priority;
	}
	@Override
	public void setPriority(Long priority) {
		this.priority = priority;
	}


	// Current methods
	private boolean isCurrentSource( T newSource ) {
		return source != null && source.equals( newSource );
	}

	private boolean isCurrentTarget( T newTarget ) {
		return target != null && target.equals( newTarget );
	}

	
	// Validation methods
	private boolean isValidSource( T newSource ) {
		if ( newSource == null ) return true;
		if ( target == null ) return true;
		
		return !target.listDescendents().contains( newSource );
	}

	private boolean isValidTarget( T newTarget ) {
		if ( newTarget == null ) return true;
		if ( source == null ) return true;
		
		return !source.listAncestors().contains( newTarget );
	}
	
	
	// Descendents methods
	private void cleanDescendents() {
		if ( source == null || target == null ) return;
		
		for ( T descendent : target.listDescendents() ) {
			getAncestors( descendent ).removeAll( source.listAncestors() );
			getAncestors( descendent ).add( descendent );
		}
	}
	
	private void updateDescendents() {
		if ( source == null || target == null ) return;
		
		for ( T descendent : target.listDescendents() ) {
			getAncestors( descendent ).addAll( source.listAncestors() );
		}
	}

	
	// Ancestors methods
	private void cleanAncestors() {
		if ( source == null || target == null ) return;
		
		for ( T ancestor : source.listAncestors() ) {
			getDescendents( ancestor ).removeAll( target.listDescendents() );
			getDescendents( ancestor ).add( ancestor );
		}
	}

	private void updateAncestors() {
		if ( source == null || target == null ) return;
		
		for ( T ancestor : source.listAncestors() ) {
			getDescendents( ancestor ).addAll( target.listDescendents() );
		}
	}
	
	
	// Children methods
	private void cleanChildren() {
		if ( source == null ) return;
		
		getChildren( source ).remove( delegator );
	}
	
	private void updateChildren() {
		if ( source == null ) return;
		
		getChildren( source ).add( delegator );
	}

	
	// Parents methods
	private void cleanParents() {
		if ( target == null ) return;
		
		getParents( target ).remove( delegator );
	}
	
	private void updateParents() {
		if ( target == null ) return;
		
		getParents( target ).add( delegator );
	}
	
	
	
	@SuppressWarnings("unchecked")
	private Set<L> getParents( T compositeEntity ) {
		try {
			Method method = MethodHelper.getMethod( compositeEntity.getClass(), "getParents" );
			return (Set<L>) method.invoke( compositeEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	@SuppressWarnings("unchecked")
	private Set<L> getChildren( T compositeEntity ) {
		try {
			Method method = MethodHelper.getMethod( compositeEntity.getClass(), "getChildren" );
			return (Set<L>) method.invoke( compositeEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	@SuppressWarnings("unchecked")
	private Set<T> getAncestors( T compositeEntity ) {
		try {
			Method method = MethodHelper.getMethod( compositeEntity.getClass(), "getAncestors" );
			return (Set<T>) method.invoke( compositeEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	@SuppressWarnings("unchecked")
	private Set<T> getDescendents( T compositeEntity ) {
		try {
			Method method = MethodHelper.getMethod( compositeEntity.getClass(), "getDescendents" );
			return (Set<T>) method.invoke( compositeEntity );
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
}