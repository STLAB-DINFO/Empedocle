package it.unifi.ing.stlab.entities.model.timed;

import java.util.Date;

public interface Time extends Comparable<Time> {

	Date getDate();

	int hashCode();
	boolean equals(Object obj);

	int compareTo(Time other);

	boolean isInfinity();
}
