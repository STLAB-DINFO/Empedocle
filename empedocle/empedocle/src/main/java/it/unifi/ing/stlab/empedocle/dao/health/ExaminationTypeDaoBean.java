package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@TransactionAttribute
public class ExaminationTypeDaoBean implements ExaminationTypeDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int count(ExaminationTypeQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExaminationType> find(ExaminationTypeQueryBuilder builder, int offset, int limit) {
		Query query = builder.getListQuery( entityManager ).setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<ExaminationType>) query.getResultList();
	}

	@Override
	public ExaminationType findById(Long id) {
		List<?> results = entityManager.createQuery( 
				"select distinct et " +
				" from ExaminationType et " +
				"  left join fetch et.type " +
				"  left join fetch et.viewers " +
				"  left join fetch et.authorizations " +
				" where et.id = :id")
			.setParameter("id", id )
			.getResultList();
		
		if ( results.size() == 0 ) {
			return null;
		}
		
		return (ExaminationType)results.get( 0 );
	}
	
	@Override
	public void delete(Long id) {
		ExaminationType toRemove = findById(id);
		entityManager.remove(toRemove);
	}
	
	@Override
	public boolean isUsed( Long id ) {
		return entityManager.createQuery( 
				"select e " +
				" from Examination e " +
				" where e.type.id = :typeId" )
			.setParameter( "typeId", id )
			.setMaxResults( 1 )
			.getResultList()
			.size() > 0 || 
			entityManager.createQuery( 
				"select a " +
				" from Agenda a " +
				" where a.examinationType.id = :typeId" )
			.setParameter( "typeId", id )
			.setMaxResults( 1 )
			.getResultList()
			.size() > 0;
	}
	
	@Override
	public ExaminationType findByExaminationId(Long id) {
	//XXX passare dai services per avere l'agenda non serve più	
		
//		List<?> result = entityManager.createQuery(
//			"select et " +
//			" from Examination e " +
//			"  join e.appointment.services s " +
//			"  join s.agenda.examinationType et " +
//			" where e.id = :id" )
//			.setParameter("id", id )
//			.setMaxResults( 1 )
//			.getResultList();
		
		
		List<?> result = entityManager.createQuery(
				"select et " +
				" from Examination e " +
				"  join e.appointment.agenda.examinationType et " +
				" where e.id = :id" )
				.setParameter("id", id )
				.setMaxResults( 1 )
				.getResultList();		
		
		if ( result.size() > 0 ) {
			return (ExaminationType)result.get( 0 );
		} else {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Viewer> findAssociatedViewer( Long examTypeId, Long qualificationId, ExaminationTypeContext context ){
		return (List<Viewer>)entityManager.createQuery(" select v.viewer " +
														" from ExaminationType et join et.viewers v " +
														" where et.id = :id and v.context = :context " +
														" and v.qualification.id = :qualificationId ")
											.setParameter("id", examTypeId)
											.setParameter("context", context)
											.setParameter("qualificationId", qualificationId)
											.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExaminationType> findAll() {
		return entityManager
					.createQuery( "from ExaminationType et" )
					.getResultList();
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	public List<ExaminationType> findWithAgendas() {
		return entityManager.createQuery( 
				"select distinct a.examinationType " 
						+ " from Agenda a "
						+ " order by a.examinationType.name" )
				.getResultList();
	}
	
	@Override
	@SuppressWarnings( "unchecked" )
	public List<ExaminationType> findWithAgendas( Long excludeId ) {
		return entityManager.createQuery( 
				"select distinct a.examinationType " 
						+ " from Agenda a "
						+ " where a.examinationType.id != :excludeId" 
						+ " order by a.examinationType.name" )
				.setParameter( "excludeId", excludeId ).getResultList();
	}	

	@Override
	public void update( ExaminationType examType ) {
		entityManager.merge( examType );
	}

	@Override
	public void save( ExaminationType examType ) {
		entityManager.persist( examType );
	}
}
