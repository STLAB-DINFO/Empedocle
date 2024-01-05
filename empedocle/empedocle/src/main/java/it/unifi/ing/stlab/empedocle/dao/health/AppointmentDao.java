package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.patients.model.Patient;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface AppointmentDao {
	
	List<Appointment> findByPatients(Set<Patient> patients);
	void update(Appointment a);
}
