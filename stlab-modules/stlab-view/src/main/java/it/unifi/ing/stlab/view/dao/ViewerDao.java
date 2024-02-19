package it.unifi.ing.stlab.view.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ViewerDao {

	Viewer findById(Long id);
	Viewer findByUuid(String uuid);
	Viewer findByName(String name);
	Viewer fetchById(Long id);

	List<Viewer> findBySuggestion(String suggestion, int limit);
	List<Viewer> find(QueryBuilder builder, int offset, int limit);

	int count(QueryBuilder builder);

	void save(Viewer v);
	void delete(Long id);
	
	boolean checkForeignKeyRestrictions(Long id);
}
