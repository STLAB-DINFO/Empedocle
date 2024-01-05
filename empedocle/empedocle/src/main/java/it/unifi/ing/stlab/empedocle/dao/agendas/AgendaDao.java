package it.unifi.ing.stlab.empedocle.dao.agendas;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Agenda;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AgendaDao {

	List<Agenda> find(QueryBuilder builder, int offset, int limit);
	List<Agenda> findAll();
	
	List<Agenda> findByPrefix(String prefix, String username, int limit);
	List<Agenda> findBySuggestion(String suggestion, int limit);
	List<Agenda> findBySuggestion(String suggestion, String username, int limit);
	List<Agenda> findUnusedAgendasBySuggestion(String suggestion, int limit);
	List<Agenda> findUnusedAgendasBySuggestion(String suggestion, int limit,
                                               List<Agenda> toExclude);
	
	List<Agenda> findByExaminationTypeId(Long id);

	Agenda findById(Long id);
	Agenda findByCode(String code);
	Agenda findByUuid(String uuid);
	
	List<Agenda> findFavoriteAgendasByUsername(String username);

	Boolean checkUnusedAgendas();
	Boolean checkUnusedAgendas(List<Agenda> toExclude);
	Boolean checkForeignKeyRestrictions(Long id);

	int count(QueryBuilder builder);
	
	void save(Agenda agenda);
	void update(Agenda agenda);
	void delete(Long id);
}
