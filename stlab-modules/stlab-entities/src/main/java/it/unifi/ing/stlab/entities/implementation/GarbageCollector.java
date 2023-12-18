package it.unifi.ing.stlab.entities.implementation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class GarbageCollector {

	//
	// Static methods & attributes
	//
	
	private static GarbageCollector instance;

	public static GarbageCollector getInstance() {
		if ( instance == null ) {
			instance = new GarbageCollector();
		}
		return instance;
	}
	public static void setInstance(GarbageCollector instance) {
		GarbageCollector.instance = instance;
	}
	
	
	//
	// Class methods & attributes
	//
	private Set<Object> trash;
	
	public GarbageCollector() {
		trash = new HashSet<Object>();
	}
	
	
	public void garbage( Object obj ) {
		if ( obj == null ) return;
		
		synchronized( this ) {
			trash.add( obj );
		}
	}

	public boolean contains( Object obj ) {
		return trash.contains( obj );
	}
	
	public void flush( GarbageAction action ) {
		synchronized( this ) {
			Iterator<Object> it = trash.iterator();
			
			while ( it.hasNext() ) {
				if ( action.execute( it.next() )) {
					it.remove();
				}
			}
		}
	}
}
