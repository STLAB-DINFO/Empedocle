package it.unifi.ing.stlab.entities.model.timed;

import java.util.Date;

public interface Time extends Comparable<Time> {

	public Date getDate();

	public int hashCode();
	public boolean equals(Object obj);

	public int compareTo(Time other);

	public boolean isInfinity();
}
