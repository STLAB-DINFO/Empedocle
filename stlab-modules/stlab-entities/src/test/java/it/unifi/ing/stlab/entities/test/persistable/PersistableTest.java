package it.unifi.ing.stlab.entities.test.persistable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import java.util.UUID;

import org.junit.Test;

public class PersistableTest {

	@Test
	public void testHashEquals() {
		String uuid = UUID.randomUUID().toString();
		Persistable p1 = new PersistableImpl( uuid ); 
		Persistable p2 = new PersistableImpl( uuid ); 
		Persistable p3 = new PersistableImpl( UUID.randomUUID().toString() ); 

		assertTrue( p1.equals( p2 ));
		assertTrue( p2.equals( p1 ));
		assertFalse( p1.equals( p3 ));
	
		assertTrue( p1.hashCode() == p2.hashCode() );
		assertFalse( p1.hashCode() == p3.hashCode() );
	}
	
}
