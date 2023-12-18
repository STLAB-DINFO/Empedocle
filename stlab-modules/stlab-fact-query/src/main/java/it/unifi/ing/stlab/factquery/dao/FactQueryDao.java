package it.unifi.ing.stlab.factquery.dao;

import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import java.util.List;

import javax.ejb.Local;

@Local
public interface FactQueryDao {

	public int count( FactQueryQueryBuilder builder );
	public List<FactQuery> find( FactQueryQueryBuilder builder, int offset, int limit );
	
	public FactQuery findById( Long id );

	public List<Fact> findFacts( Long id, FactQueryConstructor queryConstructor );
	
}
