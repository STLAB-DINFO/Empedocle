package it.unifi.ing.stlab.factquery.factory;

import it.unifi.ing.stlab.factquery.model.FactQuery;

import java.util.UUID;

public class FactQueryFactory {

	public static FactQuery createQuery(){
		return new FactQuery( UUID.randomUUID().toString() );
		
	}
	
}
