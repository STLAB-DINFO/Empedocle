package it.unifi.ing.stlab.patients.model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String place;

	@Basic
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	//
	// HashCode & Equals
	//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!( obj instanceof Address )) return false;
		
		Address other = (Address) obj;
		if ( place == null && other.getPlace() == null ) return true;
		
		return place.equals( other.getPlace() );
	}
	
	@Override
	public String toString(){
		return place;
	}
}
