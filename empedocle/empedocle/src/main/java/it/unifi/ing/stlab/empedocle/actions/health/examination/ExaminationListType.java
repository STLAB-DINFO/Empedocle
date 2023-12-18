package it.unifi.ing.stlab.empedocle.actions.health.examination;

public enum ExaminationListType {

	ALL("all", "All"), 
	BOOKED("booked", "Booked"), 
	ACCEPTED("accepted", "Accepted"), 
	RUNNING("running", "Running"), 
	SUSPENDED("suspended", "Suspended" ),
	DONE("done", "Done"), 
	CONCLUDED("concluded", "Concluded"); 
	
	private String id;
	private String name;

	private ExaminationListType(String id, String name) {
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
