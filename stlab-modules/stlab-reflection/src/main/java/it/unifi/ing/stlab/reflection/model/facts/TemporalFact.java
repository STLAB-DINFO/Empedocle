package it.unifi.ing.stlab.reflection.model.facts;

import java.util.Date;

public interface TemporalFact extends Fact {

	Date getDate();
	void setDate(Date date);
	
}
