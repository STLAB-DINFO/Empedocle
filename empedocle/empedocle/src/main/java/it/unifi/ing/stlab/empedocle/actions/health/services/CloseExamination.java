package it.unifi.ing.stlab.empedocle.actions.health.services;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface CloseExamination {

	public void startService();
	public void stopService();
	public void timeout( Timer timer );	
	
}
