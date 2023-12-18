package it.unifi.ing.stlab.sorting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.sorting.SortField;

import java.util.UUID;

import org.junit.Test;

public class SortFieldTest {

	@Test
	public void testHashCodeEquals() {
		String uuid1 = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();

		SortField sd1 = new SortField( uuid1, "s", "s asc", "s desc" );
		SortField sd2 = new SortField( uuid1, "s", "s asc", "s desc" );
		SortField sd3 = new SortField( uuid2, "s2", "s2 asc", "s2 desc" );
		
		
		assertTrue( sd1.equals( sd2 ));
		assertTrue( sd2.equals( sd1 ));
		assertFalse( sd1.equals( sd3 ));

		assertTrue( sd1.hashCode() == sd2.hashCode() );
		assertFalse( sd1.hashCode() == sd3.hashCode() );
	
	}
}
