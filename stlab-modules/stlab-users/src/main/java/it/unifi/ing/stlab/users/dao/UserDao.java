package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

@Local
public interface UserDao {

	public User findById(Long id);
	public User findByUsername( String userid );
	public List<User> findBySuggestion(String suggestion, int limit);
	
	public Set<Qualification> listUserQualifications ( Long id );
	
}
