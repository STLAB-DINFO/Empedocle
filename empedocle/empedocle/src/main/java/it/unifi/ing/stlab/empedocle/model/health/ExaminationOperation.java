package it.unifi.ing.stlab.empedocle.model.health;

public enum ExaminationOperation {

	END_EXAMINATION("end_examination", "fine visita");
	
	private String id;
	private String name;
	
	ExaminationOperation(String id, String name){
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
