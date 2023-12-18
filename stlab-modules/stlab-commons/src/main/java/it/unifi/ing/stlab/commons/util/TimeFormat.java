package it.unifi.ing.stlab.commons.util;

public enum TimeFormat {

	DATE("date","Date", "dd/MM/yyyy"),
	TIME("time","Time", "HH:mm"),
	DATETIME("datetime","DateTime", "dd/MM/yyyy HH:mm"),
	YEAR("year","Year", "yyyy"),
	MONTH_YEAR("month_year","Month/Year", "MM/yyyy");
	
	private String id;
	private String name;
	private String defaultFormat;
	
	private TimeFormat(String id, String name, String defaultFormat) {
		this.id = id;
		this.name = name;
		this.defaultFormat = defaultFormat;
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

	public String getDefaultFormat() {
		return defaultFormat;
	}
	public void setDefaultFormat(String defaultFormat) {
		this.defaultFormat = defaultFormat;
	}
	
}
