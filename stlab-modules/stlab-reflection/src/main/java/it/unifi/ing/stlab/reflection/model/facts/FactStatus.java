package it.unifi.ing.stlab.reflection.model.facts;

public enum FactStatus {
	ACTIVE("Active"), DRAFT("Draft"), REFUSED("Refused"), DERIVED("Derived");

	private String name;

	private FactStatus(String nome) {
		this.setName(nome);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}
	
}
