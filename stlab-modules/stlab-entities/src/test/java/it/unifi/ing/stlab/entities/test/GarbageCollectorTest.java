package it.unifi.ing.stlab.entities.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.implementation.GarbageAction;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;

import org.junit.Before;
import org.junit.Test;

public class GarbageCollectorTest {

	@Before
	public void setUp() {
		GarbageCollector.setInstance( new GarbageCollector() );
	}
	
	@Test
	public void testGarbage() {
		assertFalse( GarbageCollector.getInstance().contains( "ABC" ));
		assertFalse( GarbageCollector.getInstance().contains( "DEF" ));

		GarbageCollector.getInstance().garbage( "ABC" );
		GarbageCollector.getInstance().garbage( "DEF" );
		assertTrue( GarbageCollector.getInstance().contains( "ABC" ));
		assertTrue( GarbageCollector.getInstance().contains( "DEF" ));
	}

	
	@Test
	public void testFlush() {
		GarbageCollector.getInstance().garbage( "ABC" );
		GarbageCollector.getInstance().garbage( "DEF" );
	
		GarbageCollector.getInstance().flush( new FakeGarbageAction() );
		assertFalse( GarbageCollector.getInstance().contains( "ABC" ));
		assertTrue( GarbageCollector.getInstance().contains( "DEF" ));
	}
}

class FakeGarbageAction extends GarbageAction {
	
	@Override
	public boolean execute(Object obj) {
		if ( "ABC".equals( obj )) {
			return true;
		} else {
			return false;
		}
	}
	
}
