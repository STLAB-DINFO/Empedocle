package it.unifi.ing.stlab.users.model.time;

import it.unifi.ing.stlab.entities.implementation.timed.TimeRangeImpl;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable 
public class TimeRange implements it.unifi.ing.stlab.entities.model.timed.TimeRange<Time> {

	private TimeRangeImpl<Time> delegate;
	
	public TimeRange(Time l, Time r) {
		delegate = new TimeRangeImpl<Time>( l, r );
	}
	protected TimeRange() {
		delegate = new TimeRangeImpl<Time>();
	}
	
	@AttributeOverrides({
	    @AttributeOverride(name="date",column=@Column(name="start_date"))
	  })	
	@Embedded
	public Time getLeft() {
		return delegate.getLeft();
	}
	protected void setLeft(Time left) {
		delegate.setLeft(left);
	}
	
	
	@AttributeOverrides({
	    @AttributeOverride(name="date",column=@Column(name="end_date"))
	  })	
	@Embedded
	public Time getRight() {
		return delegate.getRight();
	}
	protected void setRight(Time right) {
		delegate.setRight(right);
	}
	
	
	public boolean contains(it.unifi.ing.stlab.entities.model.timed.TimeRange<?> other) {
		return delegate.contains(other);
	}
	public boolean contains(Time time) {
		return delegate.contains(time);
	}
	
	public int hashCode() {
		return delegate.hashCode();
	}
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}
	public String toString() {
		return delegate.toString();
	}

}
