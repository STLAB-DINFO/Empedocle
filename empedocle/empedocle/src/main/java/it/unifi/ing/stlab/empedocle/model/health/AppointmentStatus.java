package it.unifi.ing.stlab.empedocle.model.health;

public enum AppointmentStatus {

	BOOKED("booked", "Booked"), 
	ACCEPTED("accepted", "Accepted"), 
	CANCELED( "canceled", "Canceled" );

	private final String id;
	private final String name;

	AppointmentStatus(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return id;
	}	
}
