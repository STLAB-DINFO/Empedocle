package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.datamodel;

public class FactDataPath {
	
	private String path;
	private String data;

	public FactDataPath(String path, String data) {
		super();
		this.path = path;
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public String getData() {
		return data;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setData(String data) {
		this.data = data;
	}

}
