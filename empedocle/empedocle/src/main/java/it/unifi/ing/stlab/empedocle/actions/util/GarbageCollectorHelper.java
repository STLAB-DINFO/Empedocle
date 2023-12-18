package it.unifi.ing.stlab.empedocle.actions.util;

import javax.ejb.Local;

@Local
public interface GarbageCollectorHelper {
	
	public void flush();

}
