package it.unifi.ing.stlab.patients.dao;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.users.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PatientDao {

	int count(QueryBuilder builder);
	
	List<Patient> find(QueryBuilder builder, int offset, int limit);
	Patient findById(Long id);
	Patient findByUuid(String value);
	Patient findByIdentifier(String identifier);
	
	//XXX lasciato per garantire la compatibilità con il vecchio approccio,
	// in cui gli appointment non venivano spostati nell'ultima versione
    Patient findLastVersionById(Long id);
	
	List<Patient> findByName(String name, String surname);
	Patient findByTaxCode(String taxCode);
	List<Patient> findBySuggestion(String suggestion, int limit);
	List<Patient> findForMerge(String name, String surname, Long idExclude);
	
	PatientIdentifier findIdentifierByCode(String code);
	
	Patient fetchById(Long id);
	
	Patient mergePatients(Long patientId, Long otherId, User author);
	
	void save(Patient target);
	void update(Patient target);
	void deleteById(Long patientId, User author);

}
