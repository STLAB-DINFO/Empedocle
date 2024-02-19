package it.unifi.ing.stlab.entities.implementation;


import javax.persistence.EntityManager;

public class JpaGarbageAction extends GarbageAction {

	private final EntityManager entityManager;
	

	public JpaGarbageAction(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	@Override
	public boolean execute(Object obj) {
		if ( entityManager.contains( obj )) {
			entityManager.remove( obj );
			return true;
		} else {
			return false;
		}
	}
}
