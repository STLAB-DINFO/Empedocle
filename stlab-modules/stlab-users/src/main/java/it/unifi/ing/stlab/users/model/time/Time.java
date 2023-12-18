package it.unifi.ing.stlab.users.model.time;

import it.unifi.ing.stlab.entities.implementation.timed.TimeImpl;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Time implements it.unifi.ing.stlab.entities.model.timed.Time {

	private TimeImpl delegate;
	
	public Time(Date d ) {
		delegate = new TimeImpl( d );
	}
	protected Time() {
		delegate = new TimeImpl();
	}
	
	
	@Basic
	public Date getDate() {
		return delegate.getDate();
	}
	protected void setDate(Date d) {
		delegate.setDate(d);
	}

	
	public int hashCode() {
		return delegate.hashCode();
	}
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	
	public int compareTo(it.unifi.ing.stlab.entities.model.timed.Time other) {
		return delegate.compareTo(other);
	}

	
	@Transient
	public boolean isInfinity() {
		return delegate.isInfinity();
	}

	
	public String toString() {
		return delegate.toString();
	}

}
