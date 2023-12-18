package it.unifi.ing.stlab.empedocle.dao.health;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.patients.model.Patient;

@Local
public interface AppointmentDao {
	
	public List<Appointment> findByPatients( Set<Patient> patients );
	public void update( Appointment a );
}
