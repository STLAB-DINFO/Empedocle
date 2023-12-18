package it.unifi.ing.stlab.entities.implementation.timed;

import it.unifi.ing.stlab.entities.model.timed.Time;
import it.unifi.ing.stlab.entities.model.timed.TimeRange;

public class TimeRangeImpl<T extends Time> implements TimeRange<T> {

	private T left;
	private T right;
	
	public TimeRangeImpl(T l, T r) {
		super();

		if ( l == null || r == null ) throw new IllegalArgumentException();
		if ( !l.isInfinity() && l.compareTo( r ) > 0 ) throw new IllegalArgumentException();
			
		this.left = l;
		this.right = r;
	}

	public TimeRangeImpl() {
		super();
	}
	
	@Override
	public T getLeft() {
		return left;
	}
	public void setLeft(T left) {
		this.left = left;
	}

	@Override
	public T getRight() {
		return right;
	}
	public void setRight(T right) {
		this.right = right;
	}

	@Override
	public boolean contains( TimeRange<?> other ) {
		return ( left == null || 
					left != null && ( left.isInfinity() || left.compareTo( other.getLeft() ) <= 0 )) &&
				( right == null || 
					right != null && ( right.isInfinity() || right.compareTo( other.getRight() ) >= 0 ));	
	}

	@Override
	public boolean contains( Time time ) {
		return ( left == null || 
					left != null && ( left.isInfinity() || left.compareTo( time ) <= 0 )) &&
				( right == null || 
					right != null && ( right.isInfinity() || right.compareTo( time ) >= 0 ));	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + left.hashCode();
		result = prime * result + right.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof TimeRange )) 	return false;

		TimeRange<?> other = (TimeRange<?>) obj;
		return left.equals( other.getLeft() ) &&
				right.equals( other.getRight() );
	}
	
}
