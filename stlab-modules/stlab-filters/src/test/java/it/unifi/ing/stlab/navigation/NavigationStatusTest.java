package it.unifi.ing.stlab.navigation;

import static org.junit.Assert.assertNull;
import it.unifi.ing.stlab.navigation.NavigationStatus;

import org.junit.Before;
import org.junit.Test;

public class NavigationStatusTest {

	protected NavigationStatus navigationStatus;
	
	@Before
	public void setUp() {
		navigationStatus = new NavigationStatus();
	}
	
	@Test
	public void testClear() {
		navigationStatus.setPageSize( 50 );
		navigationStatus.setCurrentPage( 2 );
		navigationStatus.clear();
		
		
		assertNull( navigationStatus.getCurrentPage() );
	}
}
