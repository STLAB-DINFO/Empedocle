package it.unifi.ing.stlab.view.model.widgets.container;

public enum PanelOrientation {

	vertical("vertical", "vertical"), 
	horizontal("horizontal", "horizontal"),
	spaced_horizontal("spaced_horizontal", "spaced_horizontal");

	private String id; 
	private String name;

	private PanelOrientation(String id, String name) {
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
