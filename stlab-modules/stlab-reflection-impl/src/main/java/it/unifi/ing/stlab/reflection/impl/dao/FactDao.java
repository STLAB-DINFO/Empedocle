package it.unifi.ing.stlab.reflection.impl.dao;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.ejb.Local;


@Local
public interface FactDao {

	Fact findById(Long id);
	Fact findByContextId(Long contextId, Long typeId);
	Fact fetchById(Long id);
	
	void save(Fact fact);
	
	Fact fetchForStatistics(Long id);
	
	void addChildren(TypeLink tl, Fact f, User user, Time time);
	void removeChildren(FactLink fl);
	
	boolean isOwner(Long factId, String userId);
	
	boolean existsByUsedType(Long typeId);
}
