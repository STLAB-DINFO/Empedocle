package it.unifi.ing.stlab.commons.query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public interface QueryBuilder {

	public Query getCountQuery( EntityManager entityManager );
	public Query getListQuery( EntityManager entityManager );
	
}
