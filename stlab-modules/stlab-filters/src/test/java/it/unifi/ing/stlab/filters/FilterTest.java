package it.unifi.ing.stlab.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterDef;

import org.junit.Before;
import org.junit.Test;

public class FilterTest {

	protected Filter filter;
	
	@Before
	public void setUp() {
		filter = new Filter();
	}
	
	@Test
	public void testClear() {
		filter.setValue( "Hello World" );
		filter.clear();
		
		assertNull( filter.getValue() );
	}

	@Test
	public void testIsUsed() {
		assertFalse( filter.isUsed() );
		
		filter.setDefinition( new FilterDef() );
		assertFalse( filter.isUsed() );

		filter.setValue( "abc" );
		assertTrue( filter.isUsed() );

		filter.setDefinition( null );
		assertFalse( filter.isUsed() );
	}
}
