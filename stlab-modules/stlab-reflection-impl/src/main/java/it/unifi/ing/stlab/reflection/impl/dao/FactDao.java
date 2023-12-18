package it.unifi.ing.stlab.reflection.impl.dao;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.ejb.Local;


@Local
public interface FactDao {

	public Fact findById( Long id );
	public Fact findByContextId( Long contextId, Long typeId );
	public Fact fetchById( Long id );
	
	public void save( Fact fact );
	
	public Fact fetchForStatistics(Long id);
	
	public void addChildren(TypeLink tl, Fact f, User user, Time time);
	public void removeChildren(FactLink fl);
	
	boolean isOwner(Long factId, String userId);
	
	public boolean existsByUsedType(Long typeId);
}
