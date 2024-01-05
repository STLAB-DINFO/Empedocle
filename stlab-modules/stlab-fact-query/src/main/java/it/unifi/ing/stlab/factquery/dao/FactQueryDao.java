package it.unifi.ing.stlab.factquery.dao;

import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import javax.ejb.Local;
import java.util.List;

@Local
public interface FactQueryDao {

	int count(FactQueryQueryBuilder builder);
	List<FactQuery> find(FactQueryQueryBuilder builder, int offset, int limit);
	
	FactQuery findById(Long id);

	List<Fact> findFacts(Long id, FactQueryConstructor queryConstructor);
	
}
