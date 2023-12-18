package it.unifi.ing.stlab.empedocle.actions.adapters;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public LocalDateTime unmarshal( String v ) {
		return LocalDateTime.parse( v );
	}

	@Override
	public String marshal( LocalDateTime v ) {
		if ( v != null ) {
			return v.toString();
		} else {
			return null;
		}
	}
}