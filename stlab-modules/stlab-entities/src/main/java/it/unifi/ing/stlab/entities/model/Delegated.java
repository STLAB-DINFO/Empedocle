package it.unifi.ing.stlab.entities.model;


public interface Delegated<T> {

	T getDelegator();
	void setDelegator(T delegator);
	
}
