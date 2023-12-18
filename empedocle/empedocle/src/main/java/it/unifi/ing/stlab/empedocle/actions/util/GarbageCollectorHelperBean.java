package it.unifi.ing.stlab.empedocle.actions.util;

import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GarbageCollectorHelperBean implements GarbageCollectorHelper {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void flush() {
		GarbageCollector.getInstance().flush(new JpaGarbageAction(entityManager));

	}

}
