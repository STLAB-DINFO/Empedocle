package it.unifi.ing.stlab.reflection.model.facts;


public interface QuantitativeFact extends Fact {

	Quantity getQuantity();
	void setQuantity(Quantity quantity);
	
}
