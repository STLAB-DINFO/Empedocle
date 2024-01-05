package it.unifi.ing.stlab.commons.query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public interface QueryBuilder {

	Query getCountQuery(EntityManager entityManager);
	Query getListQuery(EntityManager entityManager);
	
}
