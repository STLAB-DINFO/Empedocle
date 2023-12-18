package it.unifi.ing.stlab.view.dao;

import java.util.List;

import javax.ejb.Local;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.view.model.Viewer;

@Local
public interface ViewerDao {

	public Viewer findById( Long id );
	public Viewer findByUuid( String uuid );
	public Viewer findByName( String name );
	public Viewer fetchById( Long id );

	public List<Viewer> findBySuggestion( String suggestion, int limit );
	public List<Viewer> find( QueryBuilder builder, int offset, int limit );

	public int count( QueryBuilder builder );

	public void save( Viewer v );
	public void delete( Long id );
	
	public boolean checkForeignKeyRestrictions( Long id );
}
