package it.unifi.ing.stlab.entities.model.timed;

public interface TimeRange<T extends Time> {

	public Time getLeft();
	public Time getRight();
	
	public boolean contains( TimeRange<?> other );
	public boolean contains( T time );

	public int hashCode();
	public boolean equals(Object obj);	
	
}
