package it.unifi.ing.stlab.reflection.model.facts;


public interface QuantitativeFact extends Fact {

	public Quantity getQuantity();
	public void setQuantity(Quantity quantity);
	
}
