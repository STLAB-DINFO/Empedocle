package it.unifi.ing.stlab.entities.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//FIXME perche' mezzanotte + 1 secondo???
public class DateHelper {

	public static Date createDate( String string ) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar date = Calendar.getInstance();
			date.setTime( sdf.parse( string ));
			return date.getTime();
		} catch (ParseException e) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Riporta l'inizio del giorno indicato da d
	 * @param d		Data di cui si vuole l'inizio (mezzanotte + 1 secondo)
	 * @return		Data passatagli con orario impostato a 00h:00m:01s
	 */
	public static Date startOfToday(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		
		return c.getTime();
	}

	/**
	 * Riporta l'inizio del giorno precedente a d
	 * @param d		Data di cui si vuole l'inizio del giorno prima (mezzanotte + 1 secondo)
	 * @return		Data passatagli -01d e orario 00h:00m:01s
	 */
	public static Date startOfYesterday(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		
		return c.getTime();
	}
	
	/**
	 * Riporta l'inizio del giorno successivo a d
	 * @param d		Data di cui si vuole l'inizio del giorno seguente (mezzanotte + 1 secondo)
	 * @return		Data passatagli +01d e orario 00h:00m:01s
	 */
	public static Date startOfTomorrow(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, +1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		
		return c.getTime();
	}
	
	/**
	 * Riporta la fine del giorno indicato da d
	 * @param d		Data di cui si vuole la fine (23:59:59)	
	 * @return		Data passatagli con orario impostato a 23h:59m:59s
	 */
	public static Date endOfDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		
		return c.getTime();
	}
	
}
