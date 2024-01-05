package it.unifi.ing.stlab.empedocle.actions.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

	private static final SimpleDateFormat df;

	static {
		df = new SimpleDateFormat();
	}

	public static String getString( Date date, String pattern ) {
		if( date == null )
			return null;
		
		df.applyPattern( pattern );
		df.setLenient( false );
		return df.format( date );
	}

	public static Date getDate( String s, String pattern ) throws ParseException {
		df.applyPattern( pattern );
		df.setLenient( false );
		return df.parse( s );
	}

	public static Date asDate( LocalDate localDate ) {
		if( localDate == null )
			return null;
		
		return Date.from( localDate.atStartOfDay().atZone( ZoneId.systemDefault() ).toInstant() );
	}

	public static Date asDate( LocalDateTime localDateTime ) {
		if( localDateTime == null )
			return null;
		
		return Date.from( localDateTime.atZone( ZoneId.systemDefault() ).toInstant() );
	}

	public static LocalDate asLocalDate( Date date ) {
		if( date == null )
			return null;
		
		return Instant.ofEpochMilli( date.getTime() ).atZone( ZoneId.systemDefault() )
				.toLocalDate();
	}

	public static LocalDateTime asLocalDateTime( Date date ) {
		if( date == null )
			return null;
		
		return Instant.ofEpochMilli( date.getTime() ).atZone( ZoneId.systemDefault() )
				.toLocalDateTime();
	}
}
