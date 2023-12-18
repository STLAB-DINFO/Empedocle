package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.Staff;

import java.util.UUID;

public class StaffFactory {

	public static Staff createStaff() {
		return new Staff( UUID.randomUUID().toString() );
	}
} 
