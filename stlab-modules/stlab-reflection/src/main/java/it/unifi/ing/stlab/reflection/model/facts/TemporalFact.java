package it.unifi.ing.stlab.reflection.model.facts;

import java.util.Date;

public interface TemporalFact extends Fact {

	public Date getDate();
	public void setDate(Date date);
	
}
