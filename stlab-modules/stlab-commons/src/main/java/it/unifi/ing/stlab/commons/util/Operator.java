package it.unifi.ing.stlab.commons.util;

public enum Operator {
 
	and("and", "e"), or("or", "o");
	
	private final String name;
	private final String translatedName;

	Operator(String name, String translatedName) {
		this.name = name;
		this.translatedName = translatedName;
	}
 
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getTranslatedName() {
		return translatedName;
	}

}
