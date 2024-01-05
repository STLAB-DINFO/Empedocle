package it.unifi.ing.stlab.empedocle.model.health;

public enum ExaminationStatus {

	TODO("todo", "To do", "Accettata"), 
	RUNNING("running", "Running", "In Erogazione"),
	SUSPENDED("suspended", "Suspended", "Sospesa"),
	DONE("done", "Done", "Erogata"), 
	CONCLUDED("concluded", "Concluded", "Conclusa"); 
	
	private final String id;
	private final String name;
	private final String translatedName;

	ExaminationStatus(String id, String name, String translatedName) {
		this.id = id;
		this.name = name;
		this.translatedName = translatedName;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getTranslatedName() {
		return translatedName;
	}

	@Override
	public String toString() {
		return id;
	}	
}
