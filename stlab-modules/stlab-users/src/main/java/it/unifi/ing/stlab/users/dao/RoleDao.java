package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Role;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RoleDao {

	public Role findById( Long id );
	public Role findByName( String name );
	public Role findByUuid( String uuid );
	public List<Role> findBySuggestion( String string, int limit );

	public List<Role> getAll();
}
