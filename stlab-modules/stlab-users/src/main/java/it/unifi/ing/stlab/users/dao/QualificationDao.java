package it.unifi.ing.stlab.users.dao;

import it.unifi.ing.stlab.users.model.Qualification;

import java.util.List;

import javax.ejb.Local;

@Local
public interface QualificationDao {

	public Qualification findById( Long id );
	public Qualification findByUuid( String uuid );
	public List<Qualification> findBySuggestion( String suggestion, int limit );

	public List<Qualification> getAll();
}
