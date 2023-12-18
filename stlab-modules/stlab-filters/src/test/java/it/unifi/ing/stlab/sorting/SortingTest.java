package it.unifi.ing.stlab.sorting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.sorting.Sorting;

import org.junit.Before;
import org.junit.Test;

public class SortingTest {

	private Sorting sorting;
	
	@Before
	public void setUp() {
		sorting = new Sorting();
		sorting.add( "field1", "f1 asc", "f1 desc" );
		sorting.add( "field2", "f2 asc", "f2 desc" );
	}
	
	@Test
	public void testIsSelected() {
		assertFalse( sorting.isSelected( "field1" ));

		sorting.toggle( "field1" );
		assertTrue( sorting.isSelected( "field1" ));

		sorting.toggle( "field2" );
		assertTrue( sorting.isSelected( "field2" ));
	}

	@Test
	public void testIsAscending() {
		assertFalse( sorting.isAscending( "field1" ));

		sorting.toggle( "field1" );
		assertTrue( sorting.isAscending( "field1" ));

		sorting.toggle( "field1" );
		assertFalse( sorting.isAscending( "field1" ));

		sorting.toggle( "field2" );
		assertTrue( sorting.isAscending( "field2" ));

		sorting.toggle( "field1" );
		assertTrue( sorting.isAscending( "field1" ));
	}

	@Test
	public void testSorting() {
		assertNull( sorting.getSorting() );

		sorting.toggle( "field1" );
		assertEquals( "f1 asc", sorting.getSorting() );

		sorting.toggle( "field1" );
		assertEquals( "f1 desc", sorting.getSorting() );

		sorting.toggle( "field2" );
		assertEquals( "f2 asc", sorting.getSorting() );

		sorting.toggle( "field2" );
		assertEquals( "f2 desc", sorting.getSorting() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testToggleFail() {
		sorting.toggle( "field3" );
	}

}
