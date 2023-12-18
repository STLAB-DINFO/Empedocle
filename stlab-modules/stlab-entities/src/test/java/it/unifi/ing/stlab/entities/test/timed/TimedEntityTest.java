package it.unifi.ing.stlab.entities.test.timed;

import it.unifi.ing.stlab.entities.implementation.timed.TimeImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimeRangeImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.utils.DateHelper;

import org.junit.Before;
import org.junit.Test;

public class TimedEntityTest {

	protected TimedEntity<TimeRangeImpl<TimeImpl>,TimeImpl> timedEntity1;
	protected TimedEntity<TimeRangeImpl<TimeImpl>,TimeImpl> timedEntity2;
	
	@Before
	public void setUp() {
		timedEntity1 = 
			new TimedEntityImpl<TimeRangeImpl<TimeImpl>,TimeImpl>(
				new TimeRangeImpl<TimeImpl>( 
					new TimeImpl( DateHelper.createDate( "2012-03-05")), 
					new TimeImpl( DateHelper.createDate( "2012-04-07" ))));


		timedEntity2 =
			new TimedEntityImpl<TimeRangeImpl<TimeImpl>,TimeImpl>(
				new TimeRangeImpl<TimeImpl>( 
					new TimeImpl( DateHelper.createDate( "2012-03-01")), 
					new TimeImpl( DateHelper.createDate( "2012-04-15" ))));
	}
	
	@Test
	public void testValidReference() {
		timedEntity1.isValidReference( timedEntity1 );
		timedEntity1.isValidReference( timedEntity2 );
	}
	
	@Test
	public void testInvalidReference() {
		timedEntity2.isValidReference( timedEntity1 );
	}
}
