package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.Service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//TODO test

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ServiceDaoBean implements ServiceDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Service findById(Long id) {
		return entityManager.find(Service.class, id);
	}

	@Override
	// FIXME usa indici per la ricerca? è opportuno definire indice multiplo sulle 3 colonne di ricerca?
	public Service find(String code, String description, String agendaCode){
		if(code == null || description == null || agendaCode == null)
			throw new IllegalArgumentException( "code or description or agenda code is null" );
		
		
		List<?> results = entityManager.createQuery( 
				"select distinct s " +
				" from Service s " +
				" where s.code = :code " +
				"  and s.description = :description " +
				"  and s.agenda.code = :agendaCode")
			.setParameter("code", code.trim())
			.setParameter("description", description.trim())
			.setParameter("agendaCode", agendaCode.trim())			
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		if ( results.size() > 1 ) {
			throw new RuntimeException( "Error: More than one service with code = " + code );
		}
		
		return (Service)results.get( 0 );
	}
}