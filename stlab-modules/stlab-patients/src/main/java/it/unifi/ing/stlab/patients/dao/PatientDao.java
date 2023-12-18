package it.unifi.ing.stlab.patients.dao;

import java.util.List;

import javax.ejb.Local;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.users.model.User;

@Local
public interface PatientDao {

	public int count( QueryBuilder builder );
	
	public List<Patient> find( QueryBuilder builder, int offset, int limit );
	public Patient findById( Long id );
	public Patient findByUuid( String value );	
	public Patient findByIdentifier( String identifier );
	
	//XXX lasciato per garantire la compatibilità con il vecchio approccio,
	// in cui gli appointment non venivano spostati nell'ultima versione
	public Patient findLastVersionById( Long id );
	
	public List<Patient> findByName( String name, String surname );
	public Patient findByTaxCode( String taxCode );
	public List<Patient> findBySuggestion( String suggestion, int limit );
	public List<Patient> findForMerge( String name, String surname, Long idExclude );
	
	public PatientIdentifier findIdentifierByCode( String code );
	
	public Patient fetchById(Long id);
	
	public Patient mergePatients( Long patientId, Long otherId, User author );
	
	public void save( Patient target );
	public void update( Patient target );
	public void deleteById( Long patientId, User author );

}
