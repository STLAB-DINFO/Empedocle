package it.unifi.ing.stlab.patients.model;

public enum Sex {
	M("M"), F("F"), N("N.D.");

	private String name;

	private Sex(String name) {
		this.name = name;
	}
 
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return name();
	}
}
