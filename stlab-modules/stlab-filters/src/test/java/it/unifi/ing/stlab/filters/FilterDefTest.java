package it.unifi.ing.stlab.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.filters.FilterDef;
import it.unifi.ing.stlab.filters.FilterType;

import java.util.UUID;

import org.junit.Test;

public class FilterDefTest {

	
	@Test
	public void testHashCodeEquals() {
		String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();

		FilterDef fd1 = new FilterDef( uuid1, FilterType.TEXT, "f1", "expr1", "p1", null );
		FilterDef fd2 = new FilterDef( uuid1, FilterType.TEXT, "f1", "expr1", "p1", null );
		FilterDef fd3 = new FilterDef( uuid2, FilterType.TEXT, "f2", "expr2", "p2", null );
		
		assertTrue( fd1.equals( fd2 ));
		assertTrue( fd2.equals( fd1 ));
		assertFalse( fd1.equals( fd3 ));

		assertTrue( fd1.hashCode() == fd2.hashCode() );
		assertFalse( fd1.hashCode() == fd3.hashCode() );
	}
	
}
