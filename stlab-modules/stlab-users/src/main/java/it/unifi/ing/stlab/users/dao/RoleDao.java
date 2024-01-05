package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Role;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RoleDao {

	Role findById(Long id);
	Role findByName(String name);
	Role findByUuid(String uuid);
	List<Role> findBySuggestion(String string, int limit);

	List<Role> getAll();
}
