package it.unifi.ing.stlab.empedocle.dao.health;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.patients.model.Patient;

@Stateless
@TransactionAttribute
public class AppointmentDaoBean implements AppointmentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings( "unchecked" )
	@Override
	public List<Appointment> findByPatients( Set<Patient> patients ) {
		return ( List<Appointment> ) entityManager
				.createQuery(
					"select a" + 
					" from Appointment a" +
					" where a.patient in :patients " )
				.setParameter( "patients", patients ).getResultList();
	}
	
	@Override
	public void update( Appointment target ) {
		entityManager.merge( target );
	}
}
