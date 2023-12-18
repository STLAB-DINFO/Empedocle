package it.unifi.ing.stlab.patients.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.patients.manager.PatientManager;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

@Stateless
public class PatientDaoBean implements PatientDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(QueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}
 
	@Override
	@SuppressWarnings("unchecked")
	public List<Patient> find(QueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<Patient>) query.getResultList();	
	}
	
	@Override
	public Patient findById(Long id) {
		return entityManager.find(Patient.class, id);
	}
	
	@Override
	public Patient fetchById(Long id) {
		if ( id == null ) 
			throw new IllegalArgumentException( "id is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from Patient p" +
			" left join fetch p.before b " + 
			" left join fetch p.after a " + 
			" where p.id = :pid", Patient.class )
			.setParameter( "pid", id )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (Patient)results.get( 0 );
	}	
	
	@Override
	public Patient findByIdentifier(String identifier) {
		if ( identifier == null ) 
			throw new IllegalArgumentException( "identifier is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from Patient p" +
			" where p.identifier.code = :identifier" +
			" and p.destination is null" )
			.setParameter( "identifier", identifier )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (Patient)results.get( 0 );
	}
	
	@Override
	public Patient findLastVersionById( Long id ) {
		if( id == null )
			throw new IllegalArgumentException( "id is null" );
		
		List<Patient> results = entityManager.createQuery(
					" select p " +
					" from Patient p " +
					" join p.before b " +
					" where b.id = :pid " +
					" and p.destination is null", Patient.class )
				.setParameter( "pid", id )
				.setMaxResults( 1 )
				.getResultList();
		
		if ( results.size() == 0 )
			return null;
					
		return results.get( 0 );
	}
	
	@Override
	public PatientIdentifier findIdentifierByCode(String code) {
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<PatientIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from PatientIdentifier pi " +
									" where pi.code = :code ", 
									PatientIdentifier.class )
							.setParameter( "code", code )
//							.setFlushMode(FlushModeType.COMMIT)
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 )
			return null;
			
		return results.get( 0 );
	}

	@Override
	public List<Patient> findByName(String name, String surname) {
		return entityManager.createQuery( " select p "+
									" from Patient p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null ", Patient.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.getResultList();
	}
	
	@Override
	public List<Patient> findForMerge(String name, String surname, Long excludeId) {
		return entityManager.createQuery( " select p "+
									" from Patient p " +
									" where p.name = :name " +
									" and p.surname = :surname " +
									" and p.destination is null " +
								//	" and p.identifier is null " +  // per permettere il merge anche tra anagrafiche diverse dello stesso paziente in Book
									" and p.id <> :notPid", Patient.class )
					.setParameter( "name", name )
					.setParameter( "surname", surname )
					.setParameter( "notPid", excludeId )
					.getResultList();
	}

	/**
	 * Merge manuale di pazienti
	 */
	@Override
	public Patient mergePatients( Long patientId, Long otherId, User author ) {
		Patient patient = findById( patientId );
		Patient other = findById( otherId );

		Patient master;
		Patient slave;

		PatientIdentifier patientIdentifier = patient.getIdentifier();
		PatientIdentifier otherIdentifier = other.getIdentifier();
		if ( patientIdentifier != null && otherIdentifier != null ) { 
			// merge tra anagrafiche di Book
			// master è l'anagrafica più recente 
			if ( patient.getOrigin().getTime().compareTo( other.getOrigin().getTime() ) >= 0 ) { 
				master = patient;
				slave = other;
			} else {
				master = other;
				slave = patient;
			}
		} else {
			if ( patientIdentifier != null || otherIdentifier == null ) { 
				// ci sono due casi possibili:
				// - patient è l'anagrafica di Book ed è il master
				// - oppure entrambe le anagrafiche sono senza identifier e viene presa come master
				// l'anagrafica corrente (i.e. patient)
				master = patient;
				slave = other;
			} else {
				// other è l'anagrafica di Book ed è il master
				master = other;
				slave = patient;
			}
		}

		PatientManager manager = new PatientManager();
		Time time = new Time( new Date() );
		Patient result = manager.merge( author, time, master, slave );
		
		entityManager.persist( result );
		
		return result;
	}
	
	@Override
	public void save( Patient target ) {
		entityManager.persist( target );
		flush();
	}
	
	@Override
	public void update( Patient target ) {
		entityManager.merge( target );
		flush();
	}
	
	@Override
	public void deleteById( Long id, User author ) {
		
		if( id != null ) {
			PatientManager manager = new PatientManager();
			Patient result = manager.delete( author, new Time( new Date() ), findById( id ));
			entityManager.persist( result );
		}
	}	
	
	private void flush() {
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
	}

	@Override
	public Patient findByTaxCode( String taxCode ) {
		if ( taxCode == null ) 
			throw new IllegalArgumentException( "tax code is null" );
		
		List<?> results = entityManager.createQuery( 
			"select p" +
			" from Patient p" +
			" where p.taxCode = :taxCode" +
			" and p.destination is null" )
			.setParameter( "taxCode", taxCode )
			.setMaxResults( 1 )
			.getResultList();		
			
		if ( results.size() == 0 )
			return null;
			
		return (Patient)results.get( 0 );
	}

	@Override
	public List<Patient> findBySuggestion( String suggestion, int limit ) {
		
		TypedQuery<Patient> query = entityManager.createQuery(
				" select p from Patient p " 
					+ " where CONCAT( p.surname, ' ', p.name, ' - ', p.taxCode ) like :suggestion " 
					+ " and p.destination is null", Patient.class );
		
		query.setParameter( "suggestion", '%' + suggestion + '%');
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();	
	}
	
	@Override
	public Patient findByUuid( String uuid ) {
		if (uuid == null || uuid.trim().isEmpty())
			throw new IllegalArgumentException("Parametro uuid null");

		List<Patient> results = entityManager.createQuery( 
				"select p " 
					+ " from Patient p " 
					+ " where p.uuid = :uuid", Patient.class )
			.setParameter("uuid", uuid )
			.setMaxResults( 1 )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return results.get( 0 );
	}
}
