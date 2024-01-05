package it.unifi.ing.stlab.entities.test.timed;

import it.unifi.ing.stlab.entities.implementation.timed.TimeImpl;
import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TimeTest {

	protected Time time;
	
	@Test
	public void testHashEquals() {
		Time t1 = new TimeImpl( DateHelper.createDate( "2013-03-04" ));
		Time t2 = new TimeImpl( DateHelper.createDate( "2013-03-04" ));
		Time t3 = new TimeImpl( DateHelper.createDate( "2013-03-05" ));
		Time t4 = new TimeImpl();
		Time t5 = new TimeImpl();
	
		assertTrue( t1.equals( t2 ));
		assertTrue( t2.equals( t1 ));
		assertFalse( t1.equals( t3 ));
		
		assertTrue( t4.equals( t5 ));
		assertTrue( t5.equals( t4 ));
		
		assertTrue( t1.hashCode() == t2.hashCode() );
		assertFalse( t1.hashCode() == t3.hashCode() );
		assertTrue( t4.hashCode() == t5.hashCode() );
	}
	
	@Test
	public void testInfinity() {
		Time t1 = new TimeImpl( null );
		Time t2 = new TimeImpl( DateHelper.createDate( "2013-03-04" ));
		Time t3 = new TimeImpl();
		
		assertTrue( t1.isInfinity() );
		assertFalse( t2.isInfinity() );
		assertTrue( t3.isInfinity() );
	}

	@Test
	public void testImmutableDate() {
		Time t = new TimeImpl( DateHelper.createDate( "2013-03-04" ));
		
		Date d = t.getDate();
		d.setTime( DateHelper.createDate( "2013-03-07" ).getTime() );
	
		assertEquals( DateHelper.createDate( "2013-03-07" ), d );
		assertFalse( d.equals( t.getDate() ));
	}
	
	@Test
	public void testComparable() {
		Time t1 = new TimeImpl( null );
		Time t2 = new TimeImpl( DateHelper.createDate( "2013-03-04" ));
		Time t3 = new TimeImpl( DateHelper.createDate( "2013-03-07" ));
		Time t4 = new TimeImpl( null );
		
		assertTrue( t1.compareTo( t1 ) == 0 );
		assertTrue( t1.compareTo( t2 ) > 0 );
		assertTrue( t1.compareTo( t3 ) > 0 );
		assertTrue( t1.compareTo( t4 ) == 0 );
	
		assertTrue( t2.compareTo( t2 ) == 0 );
		assertTrue( t2.compareTo( t3 ) < 0 );
		assertTrue( t2.compareTo( t4 ) < 0 );

		assertTrue( t3.compareTo( t2 ) > 0 );
		assertTrue( t3.compareTo( t3 ) == 0 );
		assertTrue( t3.compareTo( t4 ) < 0 );
		
	}
	

}
