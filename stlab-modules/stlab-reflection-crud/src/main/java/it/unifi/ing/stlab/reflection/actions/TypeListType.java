package it.unifi.ing.stlab.reflection.actions;

public enum TypeListType {

	ALL("all", "All"), 
	TEXTUAL("textual", "Textual"), 
	ENUMERATED("enumerated", "Enumerated"), 
	QUERIED("queried", "Queried"), 
	QUANTITATIVE("quantitative", "Quantitative" ),
	COMPOSITE("composite", "Composite"),
	TEMPORAL("temporal", "Temporal");
	
	private String id;
	private String name;

	private TypeListType(String id, String name) {
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
