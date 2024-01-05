package it.unifi.ing.stlab.filters;

public enum FilterType {

	TEXT("text", "text"), 
	DATE("date", "date"), 
	COMBO( "combo", "combo" ),
	SUGGESTION( "suggestion-list", "suggestion-list"),
	BOOLEAN( "boolean", "boolean");

	
	private final String id;
	private final String name;

	FilterType(String id, String name) {
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
