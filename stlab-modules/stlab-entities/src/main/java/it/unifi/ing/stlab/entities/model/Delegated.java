package it.unifi.ing.stlab.entities.model;


public interface Delegated<T> {

	public T getDelegator();
	public void setDelegator(T delegator);
	
}
