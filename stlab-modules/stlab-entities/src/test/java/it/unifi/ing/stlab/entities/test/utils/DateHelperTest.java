package it.unifi.ing.stlab.entities.test.utils;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.entities.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DateHelperTest {
	
	private Date firstDay;
	
	@Before
	public void setUp() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.YEAR, 2013);
		
		firstDay = c.getTime();
	}
	
	@Test
	public void testStartOfYesterdayFirstDay() {
		
		Date d = DateHelper.startOfYesterday(firstDay);
		
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		assertEquals(2012, c.get(Calendar.YEAR));
		// era bisestile
		assertEquals(366, c.get(Calendar.DAY_OF_YEAR));
		assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, c.get(Calendar.MINUTE));
		assertEquals(1, c.get(Calendar.SECOND));
		
		c.setTime(firstDay);
		c.set(Calendar.YEAR, 2012);
		
		firstDay = c.getTime();
		d = DateHelper.startOfYesterday(firstDay);
		c.setTime(d);
		
		// provo che funziona anche se non bisestile
		assertEquals(2011, c.get(Calendar.YEAR));
		assertEquals(365, c.get(Calendar.DAY_OF_YEAR));
		assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, c.get(Calendar.MINUTE));
		assertEquals(1, c.get(Calendar.SECOND));
		
	}

}
