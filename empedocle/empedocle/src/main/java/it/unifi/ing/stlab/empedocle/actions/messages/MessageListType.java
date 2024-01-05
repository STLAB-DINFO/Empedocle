package it.unifi.ing.stlab.empedocle.actions.messages;

public enum MessageListType {

	ALL("all", "All"), 
	INFO("info", "Info"), 
	WARNING("warning", "Warning"), 
	SEVERE( "severe", "Severe" );
	
	private final String id;
	private final String name;

	MessageListType(String id, String name) {
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
