package it.unifi.ing.stlab.navigation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.navigation.NavigationStatus;
import it.unifi.ing.stlab.navigation.Navigator;

import org.junit.Before;
import org.junit.Test;


public class NavigatorTest {

	protected NavigationStatus navigationStatus;
	protected FakeNavigator navigator;
	
	@Before
	public void setUp() {
		navigationStatus = new NavigationStatus();
		navigationStatus.setPageSize( 50 );
		 
		navigator = new FakeNavigator();
		navigator.setNavigationStatus( navigationStatus );
		navigator.setItemCount( 123 );
	}
	
	@Test
	public void testPageCount() {
		assertEquals( new Integer( 3 ), navigator.getPageCount() );
	}

	@Test
	public void testCurrent() {
		assertEquals( new Integer( 1 ), navigator.getCurrentPage() );

		navigator.setCurrentPage( 2 );
		assertEquals( new Integer( 2 ), navigator.getCurrentPage() );

		navigator.setCurrentPage( 3 );
		assertEquals( new Integer( 3 ), navigator.getCurrentPage() );

		navigator.setCurrentPage( 1 );
		assertEquals( new Integer( 1 ), navigator.getCurrentPage() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testCurrentBeforeFirst() {
		navigator.setCurrentPage( 0 );
	}

	@Test( expected = RuntimeException.class )
	public void testCurrentAfterLast() {
		navigator.setCurrentPage( 4 );
	}
	
	@Test
	public void testItemsEmpty() {
		navigator.setItemCount( 0 );
		
		assertEquals( new Integer( 0 ),  navigator.getPageCount() );
		assertEquals( new Integer( 0 ),  navigator.getCurrentPage() );
	}

	@Test
	public void testFirstItem() {
		navigator.setCurrentPage( 1 );
		assertEquals( new Integer( 1 ), navigator.getFirstItem() );

		navigator.setCurrentPage( 2 );
		assertEquals( new Integer( 51 ), navigator.getFirstItem() );

		navigator.setCurrentPage( 3 );
		assertEquals( new Integer( 101 ), navigator.getFirstItem() );
	}

	@Test
	public void testLastItem() {
		navigator.setCurrentPage( 1 );
		assertEquals( new Integer( 50 ), navigator.getLastItem() );
		
		navigator.setCurrentPage( 2 );
		assertEquals( new Integer( 100 ), navigator.getLastItem() );

		navigator.setCurrentPage( 3 );
		assertEquals( new Integer( 123 ), navigator.getLastItem() );
	}

	@Test
	public void testExistsPage() {
		assertTrue( navigator.existsPage( 1 ));
		assertTrue( navigator.existsPage( 2 ));
		assertTrue( navigator.existsPage( 3 ));

		assertFalse( navigator.existsPage( 0 ));
		assertFalse( navigator.existsPage( -1 ));
		assertFalse( navigator.existsPage( 5 ));
	}

	
	@Test
	public void testHasNext() {
		navigator.setItemCount( new Integer( 5000 ));
		navigator.setCurrentPage( 1 );
		assertTrue( navigator.hasNext() );
		
		navigator.setCurrentPage( 100 );
		assertFalse( navigator.hasNext() );
	}
	

	@Test
	public void testHasPrevious() {
		navigator.setItemCount( new Integer( 5000 ));
		navigator.setCurrentPage( 1 );
		assertFalse( navigator.hasPrevious() );
		
		navigator.setCurrentPage( 100 );
		assertTrue( navigator.hasPrevious() );
	}
	
	@Test
	public void testMoveNext() {
		navigator.setItemCount( new Integer( 5000 ));
		navigator.setCurrentPage( 1 );
		navigator.moveNext();
		assertEquals( new Integer( 2 ), navigator.getCurrentPage() );
		
		navigator.setCurrentPage( 100 );
		navigator.moveNext();
		assertEquals( new Integer( 100 ), navigator.getCurrentPage() );
	}

	@Test
	public void testMovePrevious() {
		navigator.setItemCount( new Integer( 5000 ));
		navigator.setCurrentPage( 1 );
		navigator.movePrevious();
		assertEquals( new Integer( 1 ), navigator.getCurrentPage() );
		
		navigator.setCurrentPage( 100 );
		navigator.movePrevious();
		assertEquals( new Integer( 99 ), navigator.getCurrentPage() );
	}
	
	@Test
	public void testRefreshCurrentPage() {
		navigationStatus.setPageSize( 50 );
		navigator.setItemCount( 500 );
		
		navigationStatus.setCurrentPage( 3 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 3 ), navigator.getCurrentPage() );

		navigationStatus.setCurrentPage( 80 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 10 ), navigator.getCurrentPage() );

		navigationStatus.setCurrentPage( 0 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 1 ), navigator.getCurrentPage() );
	
		navigator.setItemCount( 0 );
		
		navigationStatus.setCurrentPage( 3 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 0 ), navigator.getCurrentPage() );

		navigationStatus.setCurrentPage( 80 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 0 ), navigator.getCurrentPage() );

		navigationStatus.setCurrentPage( 0 );
		navigator.refreshCurrentPage();
		assertEquals( new Integer( 0 ), navigator.getCurrentPage() );
	
	}
}

class FakeNavigator extends Navigator {

	private Integer itemCount;

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
}
