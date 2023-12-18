package it.unifi.ing.stlab.entities.test.timed;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.entities.implementation.timed.TimeImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimeRangeImpl;
import it.unifi.ing.stlab.entities.model.timed.TimeRange;
import it.unifi.ing.stlab.entities.utils.DateHelper;

import org.junit.Before;
import org.junit.Test;

public class TimeRangeTest {

	protected TimeRange<TimeImpl> tr;
	
	@Before
	public void setUp() {
		tr = new TimeRangeImpl<TimeImpl>( 
			new TimeImpl( DateHelper.createDate( "2012-03-01")), 
			new TimeImpl( DateHelper.createDate( "2012-04-15" )));
	}
	
	public void testHashEquals() {
		TimeRange<TimeImpl> tr1 = new TimeRangeImpl<TimeImpl>( 
			new TimeImpl( DateHelper.createDate( "2012-01-01")), 
			new TimeImpl( DateHelper.createDate( "2012-02-21" )));

		TimeRange<TimeImpl> tr2 = new TimeRangeImpl<TimeImpl>( 
			new TimeImpl( DateHelper.createDate( "2012-01-01")), 
			new TimeImpl( DateHelper.createDate( "2012-02-21" )));

		TimeRange<TimeImpl> tr3 = new TimeRangeImpl<TimeImpl>( 
			new TimeImpl( DateHelper.createDate( "2012-02-09")), 
			new TimeImpl( DateHelper.createDate( "2012-05-27" )));

		assertTrue( tr1.equals( tr2 ));
		assertTrue( tr2.equals( tr1 ));
		assertFalse( tr1.equals( tr3 ));
	
		assertTrue( tr1.hashCode() == tr2.hashCode() );
		assertFalse( tr1.hashCode() == tr3.hashCode() );
		
	}
	
	
	@Test( expected = IllegalArgumentException.class )
	public void testLowerNull() {
		new TimeRangeImpl<TimeImpl>( null, new TimeImpl() );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testUpperNull() {
		new TimeRangeImpl<TimeImpl>( new TimeImpl(), null );
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testInvalidRange() {
		new TimeRangeImpl<TimeImpl>( 
			new TimeImpl( DateHelper.createDate( "2012-31-12")), 
			new TimeImpl( DateHelper.createDate( "2012-01-01" )));
	}
	
	@Test
	public void testContainsBefore() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-01-01")), 
				new TimeImpl( DateHelper.createDate( "2012-02-21" )));
		
		assertFalse( tr.contains( other ));
	}

	@Test
	public void testContainsLeftInterserct() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-01-01")), 
				new TimeImpl( DateHelper.createDate( "2012-03-10" )));
		
		assertFalse( tr.contains( other ));
	}

	@Test
	public void testContains() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-03-05")), 
				new TimeImpl( DateHelper.createDate( "2012-04-07" )));
		
		assertTrue( tr.contains( other ));
	}
	
	@Test
	public void testContainsRightInterserct() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-03-15")), 
				new TimeImpl( DateHelper.createDate( "2012-05-09" )));
		
		assertFalse( tr.contains( other ));
	}
	
	@Test
	public void testContainsAfter() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-06-12")), 
				new TimeImpl( DateHelper.createDate( "2012-09-09" )));
		
		assertFalse( tr.contains( other ));
	}
	
	@Test
	public void testContainsLeftInfinity() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl(), 
				new TimeImpl( DateHelper.createDate( "2012-05-09" )));
		
		assertTrue( other.contains( tr ));
	}
	
	@Test
	public void testContainsLeftNull() {
		TimeRangeImpl<TimeImpl> other = new TimeRangeImpl<TimeImpl>();
		other.setLeft( null );
		other.setRight( new TimeImpl( DateHelper.createDate( "2012-05-09" )));
		
		assertTrue( other.contains( tr ));
	}	

	@Test
	public void testContainsRightInfinity() {
		TimeRange<TimeImpl> other = new TimeRangeImpl<TimeImpl>( 
				new TimeImpl( DateHelper.createDate( "2012-01-07" )), 
				new TimeImpl());
		
		assertTrue( other.contains( tr ));
	}
	
	@Test
	public void testContainsRightNull() {
		TimeRangeImpl<TimeImpl> other = new TimeRangeImpl<TimeImpl>();
		other.setLeft( new TimeImpl( DateHelper.createDate( "2012-01-07" )) );
		other.setRight( null );
		
		assertTrue( other.contains( tr ));
	}		
	
}
