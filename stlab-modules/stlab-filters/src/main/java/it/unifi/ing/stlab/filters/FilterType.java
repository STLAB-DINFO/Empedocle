package it.unifi.ing.stlab.filters;

public enum FilterType {

	TEXT("text", "text"), 
	DATE("date", "date"), 
	COMBO( "combo", "combo" ),
	SUGGESTION( "suggestion-list", "suggestion-list"),
	BOOLEAN( "boolean", "boolean");

	
	private String id;
	private String name;

	private FilterType(String id, String name) {
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
