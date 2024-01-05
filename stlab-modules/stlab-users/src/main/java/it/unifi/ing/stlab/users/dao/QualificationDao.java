package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;

import javax.ejb.Local;
import java.util.List;

@Local
public interface QualificationDao {

	Qualification findById(Long id);
	Qualification findByUuid(String uuid);
	List<Qualification> findBySuggestion(String suggestion, int limit);

	List<Qualification> getAll();
}
