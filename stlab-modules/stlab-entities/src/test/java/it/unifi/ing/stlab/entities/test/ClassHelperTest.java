package it.unifi.ing.stlab.entities.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.utils.ClassHelper;

import org.junit.Test;

public class ClassHelperTest {

	@Test
	public void testInstanceOf() {
		assertTrue( ClassHelper.instanceOf( "Hello", String.class ));
		assertFalse( ClassHelper.instanceOf( "Hello", Long.class ));
	}
	
	@Test
	public void testCast() {
		assertNotNull( ClassHelper.cast( "String", String.class ));
	}
}

