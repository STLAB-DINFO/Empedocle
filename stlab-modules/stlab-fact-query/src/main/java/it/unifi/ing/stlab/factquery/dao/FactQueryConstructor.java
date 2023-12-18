package it.unifi.ing.stlab.factquery.dao;

import it.unifi.ing.stlab.factquery.model.FactQuery;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class FactQueryConstructor {

	private Map<String, Object> additionalParams; 
	
	protected FactQueryConstructor() {
		additionalParams = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getAdditionalParams() {
		return Collections.unmodifiableMap( additionalParams );
	}
	public void addAdditionalParams(String key, Object value) {
		additionalParams.put( key, value );
	}
	
	public abstract Query buildQuery( EntityManager entityManager, FactQuery factQuery );

	protected void resolveParameters(FactQuery factQuery, Query query) {
		for( String paramName : getAdditionalParams().keySet() ) {
			query.setParameter( paramName, getAdditionalParams().get( paramName ) );
		}
		
	}
	
}
