package it.unifi.ing.stlab.empedocle.actions.health.examination;

public enum ExaminationListType {

	ALL("all", "All"), 
	BOOKED("booked", "Booked"), 
	ACCEPTED("accepted", "Accepted"), 
	RUNNING("running", "Running"), 
	SUSPENDED("suspended", "Suspended" ),
	DONE("done", "Done"), 
	CONCLUDED("concluded", "Concluded"); 
	
	private final String id;
	private final String name;

	ExaminationListType(String id, String name) {
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
