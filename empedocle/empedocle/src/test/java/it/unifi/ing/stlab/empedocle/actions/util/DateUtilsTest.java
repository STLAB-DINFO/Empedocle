package it.unifi.ing.stlab.empedocle.actions.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void test() throws ParseException {
		Date date = DateUtils.getDate( "20180606115632.000+0200", "yyyyMMddHHmmss.SSSZ" );

		Calendar calendar = new GregorianCalendar();
		calendar.setTime( date );
		assertEquals( 2018, calendar.get( Calendar.YEAR ) );
		assertEquals( 6, calendar.get( Calendar.MONTH ) + 1 );
		assertEquals( 6, calendar.get( Calendar.DAY_OF_MONTH ) );
		assertEquals( 11, calendar.get( Calendar.HOUR_OF_DAY ) );
		assertEquals( 56, calendar.get( Calendar.MINUTE ) );
		assertEquals( 32, calendar.get( Calendar.SECOND ) );
		assertEquals( 0, calendar.get( Calendar.MILLISECOND ) );
	}
}
