package it.unifi.ing.stlab.entities.model.timed;

public interface TimeRange<T extends Time> {

	Time getLeft();
	Time getRight();
	
	boolean contains(TimeRange<?> other);
	boolean contains(T time);

	int hashCode();
	boolean equals(Object obj);
	
}
