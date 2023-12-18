package it.unifi.ing.stlab.empedocle.dao.messages;

import it.unifi.ing.stlab.empedocle.model.messages.Message;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MessageDaoBean implements MessageDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(MessageQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Message> find(MessageQueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<Message>) query.getResultList();
	}
	
	@Override
	public int countByPatientId(Long id) {
		return ( (Long) entityManager.createQuery( 
				"select count (distinct m ) from Message m "
				+ "where m.patient.id = :id")
			.setParameter("id", id)
			.getSingleResult() ).intValue();
	}
	
	@Override
	public Message findById(Long id) {
		List<?> results = entityManager.createQuery( 
				"select m from Message m" +
				" where m.id = :id")
			.setParameter("id", id)
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (Message)results.get( 0 );
	}
	
	public void update(Message target) {
		entityManager.merge(target);
	}
	
	public void save(Message target) {
		entityManager.persist(target);
	}
}
