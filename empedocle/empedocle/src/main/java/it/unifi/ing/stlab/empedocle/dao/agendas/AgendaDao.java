package it.unifi.ing.stlab.empedocle.dao.agendas;

import java.util.List;

import javax.ejb.Local;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Agenda;

@Local
public interface AgendaDao {

	public List<Agenda> find( QueryBuilder builder, int offset, int limit );
	public List<Agenda> findAll();
	
	public List<Agenda> findByPrefix( String prefix, String username, int limit );
	public List<Agenda> findBySuggestion( String suggestion, int limit );
	public List<Agenda> findBySuggestion( String suggestion, String username, int limit );
	public List<Agenda> findUnusedAgendasBySuggestion( String suggestion, int limit );
	public List<Agenda> findUnusedAgendasBySuggestion( String suggestion, int limit,
			List<Agenda> toExclude );
	
	public List<Agenda> findByExaminationTypeId( Long id );

	public Agenda findById( Long id );
	public Agenda findByCode( String code );
	public Agenda findByUuid( String uuid );
	
	public List<Agenda> findFavoriteAgendasByUsername( String username );

	public Boolean checkUnusedAgendas();
	public Boolean checkUnusedAgendas( List<Agenda> toExclude );
	public Boolean checkForeignKeyRestrictions( Long id );

	public int count( QueryBuilder builder );
	
	public void save( Agenda agenda );
	public void update( Agenda agenda );
	public void delete( Long id );
}
