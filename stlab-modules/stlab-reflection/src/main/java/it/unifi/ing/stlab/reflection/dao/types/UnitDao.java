package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.types.Unit;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UnitDao {
	
	public Unit findByUuid( String uuid );
	public List<Unit> findAll();
	
}
