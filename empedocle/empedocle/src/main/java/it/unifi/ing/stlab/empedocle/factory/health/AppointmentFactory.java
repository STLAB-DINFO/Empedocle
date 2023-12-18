package it.unifi.ing.stlab.empedocle.factory.health;

import it.unifi.ing.stlab.empedocle.model.health.Appointment;

import java.util.UUID;

public class AppointmentFactory {

	public static Appointment createAppointment() {
		return new Appointment( UUID.randomUUID().toString() );
	}
}
