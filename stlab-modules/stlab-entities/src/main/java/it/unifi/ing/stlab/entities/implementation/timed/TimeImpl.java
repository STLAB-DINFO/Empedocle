package it.unifi.ing.stlab.entities.implementation.timed;

import it.unifi.ing.stlab.entities.model.timed.Time;

import java.util.Date;

public class TimeImpl implements Time {

	private Date date;

	public TimeImpl() {
		super();
	}

	public TimeImpl(Date d ) {
		super();
		setDate( d );
	}

	public Date getDate() {
		if(date == null)
			return null;
		
		return (Date)date.clone();
	}
	public void setDate(Date d) {
		if ( d == null ) {
			date = null;
		} else {
			this.date = (Date)d.clone();
		}
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof Time )) return false;
		
		Time other = (Time) obj;
		if ( isInfinity() && !other.isInfinity() ||
			 !isInfinity() && other.isInfinity() ) return false;
		
		if ( isInfinity() && other.isInfinity() ) return true;
		
		return date.equals( other.getDate() );
	}

	@Override
	public int compareTo(Time other) {
		if ( isInfinity() && other.isInfinity() ) return 0;
		if ( isInfinity() && !other.isInfinity() ) return 1;
		if ( !isInfinity() && other.isInfinity() ) return -1;
	
		return date.compareTo( other.getDate() );
	}

	public boolean isInfinity() {
		return date == null;
	}
	
	
}
