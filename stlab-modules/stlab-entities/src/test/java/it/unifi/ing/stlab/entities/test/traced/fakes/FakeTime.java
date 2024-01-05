package it.unifi.ing.stlab.entities.test.traced.fakes;

import it.unifi.ing.stlab.entities.implementation.timed.TimeImpl;
import it.unifi.ing.stlab.entities.model.timed.Time;

import java.util.Date;

public class FakeTime implements Time {

	private final TimeImpl delegate;
	
	public FakeTime() {
		delegate = new TimeImpl();
	}
	

	public Date getDate() {
		return delegate.getDate();
	}
	public void setDate(Date d) {
		delegate.setDate(d);
	}

	
	public int hashCode() {
		return delegate.hashCode();
	}
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	
	public int compareTo(Time other) {
		return delegate.compareTo(other);
	}

	
	public boolean isInfinity() {
		return delegate.isInfinity();
	}

	
	public String toString() {
		return delegate.toString();
	}
}
