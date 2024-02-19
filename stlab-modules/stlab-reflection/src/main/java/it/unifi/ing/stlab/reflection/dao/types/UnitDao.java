package it.unifi.ing.stlab.reflection.dao.types;

import it.unifi.ing.stlab.reflection.model.types.Unit;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UnitDao {
	
	Unit findByUuid(String uuid);
	List<Unit> findAll();
	
}
