package it.unifi.ing.stlab.sorting;


public class SortField {

	private String uuid;
	private String name;
	private String ascending;
	private String descending;
	

	public SortField(String uuid, String name, String ascending,
			String descending) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.ascending = ascending;
		this.descending = descending;
	}

	public SortField() {
		super();
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getAscending() {
		return ascending;
	}
	public void setAscending(String ascending) {
		this.ascending = ascending;
	}
	
	
	public String getDescending() {
		return descending;
	}
	public void setDescending(String descending) {
		this.descending = descending;
	}
	
	//
	// HashCode & Equals
	//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uuid.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof SortField )) return false;

		SortField other = (SortField) obj;
		return uuid.equals( other.getUuid() );
	}

	
}
