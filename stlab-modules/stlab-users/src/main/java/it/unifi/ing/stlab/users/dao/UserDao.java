package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface UserDao {

	User findById(Long id);
	User findByUsername(String userid);
	List<User> findBySuggestion(String suggestion, int limit);
	
	Set<Qualification> listUserQualifications(Long id);
	
}
