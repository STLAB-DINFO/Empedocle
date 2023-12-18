package it.unifi.ing.stlab.factquery.dao;

import it.unifi.ing.stlab.commons.qualifier.StatefulBean;
import it.unifi.ing.stlab.factquery.model.FactQuery;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful @StatefulBean
@TransactionAttribute
public class FactQueryDaoStatefulBean implements FactQueryDao {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
//	@EJB
//	private FactDao factDao;
	
	@Inject @Default
	private FactQueryDao delegate;
	
	@Override
	public int count(FactQueryQueryBuilder builder) {
		return delegate.count( builder );
	}

	@Override
	public List<FactQuery> find(FactQueryQueryBuilder builder, int offset, int limit) {
		return delegate.find( builder, offset, limit );
	}

	@Override
	public FactQuery findById(Long id) {
		return delegate.findById( id );
	}

	@Override
	public List<Fact> findFacts(Long id, FactQueryConstructor queryConstructor) {
		return delegate.findFacts( id, queryConstructor );
	}

}
