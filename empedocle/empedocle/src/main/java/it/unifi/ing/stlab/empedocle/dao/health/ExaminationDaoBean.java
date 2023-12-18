package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Authorization;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationOperation;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.RecursiveResolveLazyLoadVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.view.dao.ViewerDao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@TransactionAttribute
public class ExaminationDaoBean implements ExaminationDao {

	@EJB
	private FactDao factDao;
	
	@EJB
	private TypeDao typeDao;
	
	@EJB 
	private ViewerDao viewerDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Fact resume(Fact f, Patient p) {
		String q = "SELECT f FROM FactImpl f" +
				" JOIN f.context.appointment.patient.after aa " +
				" WHERE f.type = :type" +
				" AND aa.id = :patient" +
				" AND f.context.status != :notStatusExam" +
				" AND f.status != :notStatusFact" +
				" AND f.destination is null" +
				" ORDER BY f.origin.time DESC";
		
		List<Fact> result = entityManager.createQuery(q)
			.setMaxResults(1)
			.setParameter("type", f.getType())
			.setParameter("patient", p.getId())
			.setParameter("notStatusExam", ExaminationStatus.RUNNING)
			.setParameter("notStatusFact", FactStatus.REFUSED)
			// NB: f è ancora TRANSIENT !
//			.setParameter("currFact", f)
			.getResultList();
		
		if(result.size() < 1) {
			return null;
		}
		else {
			return result.iterator().next();
		}
	}
	
	@Override
	public Long countUserExaminationsByStatus(String userid, ExaminationStatus status, Date start, Date end) {
		String q = "select count( distinct e )" +
				" from Examination e" +
				" where e.author.userid = :id" +
				" and e.status = :status " +
				" and e.appointment.date >= :start " +
				" and e.appointment.date <= :end";
		
		return (Long)entityManager.createQuery(q)
				.setParameter("id", userid)
				.setParameter("status", status)
				.setParameter("start", start)
				.setParameter("end", end)
				.getSingleResult();
	}
	
	@Override
	public int count(ExaminationQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Examination> find(ExaminationQueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<Examination>) query.getResultList();
	}

	@Override
	public Examination findById(Long id) {
		return entityManager.find(Examination.class, id);
	}
	
	@Override
	public int countPatientHistory(Long patientId, Long examFromId, 
			Set<ExaminationStatus> statuses, Set<Agenda> agendas ) {
		if(patientId == null || examFromId == null)
			throw new IllegalArgumentException("id paziente o id esame sono nulli");
		
		return ((Long)entityManager.createQuery("select count ( distinct e ) " +
						  						" from Examination e " +
						  						" join e.appointment.patient.after aa " +
						  						" where aa.id = :pid " +
												" and e.status in :statuses " +
												" and e.appointment.agenda in :agendas " +
												" and e.id != :examId ")						  						
						  				.setParameter("pid", patientId)
						  				.setParameter("examId", examFromId)
						  				.setParameter("statuses", statuses)
						  				.setParameter("agendas", agendas)
				  						.getSingleResult()).intValue();
		
	}
	
	@Override
	public boolean hasPatientHistory( Long patientId ) {
		if( patientId == null )
			throw new IllegalArgumentException("id paziente è nullo");
		
		 return ((Long)entityManager.createQuery("select count ( distinct e ) " +
					" from Examination e " +
					" join e.appointment.patient.after aa " +
					" where aa.id = :pid " )						  						
			.setParameter("pid", patientId)
			.getSingleResult()).intValue() > 0 ? true : false;
	}
	
	// FIXME ripulire
	@Override
	public boolean hasPatientHistory(Long patientId, Set<ExaminationStatus> statuses, Set<Agenda> agendas ) {
		if(patientId == null)
			throw new IllegalArgumentException("id paziente è nullo");
		
		StringBuffer query = new StringBuffer();
		query.append("select e.id ")
			.append(" from Examination e ")
			.append(" join e.appointment a ")
			.append(" join a.agenda ag ")
			.append(" join a.patient.after aa ")
			.append(" where aa.id = :pid ");
		
		if(agendas != null && agendas.size() > 0) {
			query.append(" and (");
			
			int count_ag = 0;
			for(Agenda a : agendas) {
				query.append(" ag.id = '" + a.getId() +"' ");
				if(count_ag != agendas.size() - 1) {
					query.append(" or ");
				}
				count_ag++;
			}
			
			query.append(")");
		}
		
		if(statuses != null && statuses.size() > 0) {
			query.append(" and (");
			
			int count_st = 0;
			for(ExaminationStatus e : statuses) {
				query.append(" e.status = '" + e.getId() +"' ");
				if(count_st != statuses.size() - 1) {
					query.append(" or ");
				}
				count_st++;
			}
			
			query.append(")");
		}
		
		return (entityManager.createQuery(query.toString())
			.setMaxResults(1)
			.setParameter("pid", patientId)
			.getResultList().size() > 0);
	}
	
	@Override
	// FIXME eliminare IN?
	public List<Examination> findPatientHistory( Long patientId, Long examFromId, 
			Set<ExaminationStatus> statuses, Set<Agenda> agendas, int offset, int limit ) {
		if ( patientId == null || examFromId == null )
			throw new IllegalArgumentException( "id paziente o id esame sono nulli" );
		
		TypedQuery<Examination> query = entityManager.createQuery(
			" select distinct e from Examination e " 
					+ " join e.appointment.patient.after aa " 
					+ " where e.status in :statuses " 
					+ " and e.appointment.agenda in :agendas " 
					+ " and e.id != :examId " 
					+ " and aa.id = :pid " 
					+ " order by e.appointment.date DESC", Examination.class );
		
		query.setParameter( "pid", patientId )
			.setParameter( "examId", examFromId )
			.setParameter( "statuses", statuses )
			.setParameter( "agendas", agendas );
		
		query.setFirstResult(offset);
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	public List<Examination> findPatientLastExams( Long patientId, Long lastExamId,  int examsNum){
		TypedQuery<Examination> query = entityManager.createQuery(
				" select distinct e from Examination e "
						+ " join e.appointment.patient.after aa"
						+ " where e.id != :examId "
						+ " and aa.id = :pid "
						+ " order by e.appointment.date DESC", Examination.class );

		query.setParameter( "pid", patientId )
				.setParameter( "examId", lastExamId );

		query.setMaxResults( examsNum );

		return query.getResultList();

	}
	

	@Override
	public ExaminationDetails fetchById(Long examinationId, Long qualificationId, ExaminationTypeContext context ) {
		long start = System.currentTimeMillis();
		
		if ( examinationId == null || qualificationId == null ) return null;
		
		ExaminationDetails result = new ExaminationDetails();

		// init examination
		result.setExamination( findById( examinationId ));
		if ( result.getExamination() == null ) return null;

		
		// find type
		if ( result.getExamination().getType() == null || 
			 result.getExamination().getType().getType() == null ) return result;
			
		typeDao.fetchById( result.getExamination().getType().getType().getId() );
		
		
		// find fact
		Long typeId = result.getExamination().getType().getType().getId();
		result.setFact( factDao.findByContextId(examinationId, typeId ));
		if ( result.getFact() == null )
			return result;
		
		factDao.fetchById( getId( result.getFact() ) );
		
		// FIXME trovare soluzione più elegante?
		// aggiunto per risolvere crash quando si stampa visita con storia passata
		result.getFact().accept(new RecursiveResolveLazyLoadVisitor());		

		// init viewer
		for ( ViewerUse viewerUse  : result.getExamination().getType().listViewers() ) {
			if ( viewerUse.getContext().equals( context ) &&
				 viewerUse.getQualification().getId().equals( qualificationId )) {
				
				result.setViewer( viewerDao.fetchById( viewerUse.getViewer().getId() ));
			}
		}
		
		long end = System.currentTimeMillis();
		
		// FIXME ripulire
		System.out.println( "TEMPO CARICAMENTO DATI:" + ( end - start ));
		
		
		return result;
	}
	
	@Override
	public ExaminationDetails fetchByExaminationViewer( Long examinationId, Long qualificationId, Long viewerId ) {
		ExaminationDetails result = new ExaminationDetails();

		// init examination
		result.setExamination( findById( examinationId ));
		if ( result.getExamination() == null ) return null;

		// prefetch qualifiche user
		entityManager.createQuery("select u from User u left join fetch u.qualifications q where u = :user")
				.setParameter("user", result.getExamination().getAuthor()).getResultList();
		
		// find type
		if ( result.getExamination().getType() == null || 
			 result.getExamination().getType().getType() == null ) return null;
			
		typeDao.fetchById( result.getExamination().getType().getType().getId() );
		
		// find fact
		Long typeId = result.getExamination().getType().getType().getId();
		result.setFact( factDao.findByContextId(examinationId, typeId ));
		if ( result.getFact() == null )
			return null;
		
		factDao.fetchById( getId( result.getFact() ) );
		
		// FIXME trovare soluzione più elegante?
		// aggiunto per risolvere crash quando si stampa visita con storia passata
		result.getFact().accept(new RecursiveResolveLazyLoadVisitor());

		//FIXME da ricontrollare e testare
		// controllo che il viewer sia associato effettivamente con la mia qualifica
//		int size = ((Long)entityManager.createQuery(" select count( distinct v) from ViewerUse v " +
//									" where v.viewer.id = :viewerId " +
//									" and v.qualification.id = :qualificationId")
//								.setParameter("viewerId", viewerId)
//								.setParameter("qualificationId", qualificationId)
//								.getSingleResult()).intValue();
		
//		if(size != 1) return null;
		
		result.setViewer( viewerDao.fetchById( viewerId ));
		
		// pre-fetch dei services per poter leggere agenda
		// FIXME serve ancora? adesso l'agenda è associata anche alla visita!
		entityManager.createQuery("select a from Appointment a, FactImpl f left join fetch a.services ss where f.context = a and f.id = :id")
			.setParameter("id", getId( result.getFact() ) ).getResultList();
		
		if ( result.getViewer() == null ) return null;
		
		return result;
	}

	@Override
	public void update( Examination e ) {
		entityManager.merge(e);
	}
	
	public void save( Examination e ) {
		entityManager.persist(e);
	}

	@Override
	public boolean hasPermission( Long examinationId, Long qualificationId, ExaminationOperation operation ) {
		ExaminationType type = findById( examinationId ).getType();
		
		for (Authorization auth : type.listAuthorizations()) {
			if( operation.equals( auth.getExamOperation() ) && 
					qualificationId.equals( auth.getQualification().getId() ) )
				return true;
		}
		
		return false;
		
	}
	
	//TODO test
	@Override
	public Examination findByAppointmentCodes(String bookingCode, String acceptanceCode) {
		if ( bookingCode == null && acceptanceCode == null ) 
			throw new IllegalArgumentException( "bookingCode and acceptanceCode are null" );
		
		Query query;
		
		if ( acceptanceCode == null ) {
			query = entityManager.createQuery( "select e" +
					" from Examination e left join e.appointment" +
					" where e.appointment.bookingCode = :bookingCode" )
					.setParameter( "bookingCode", bookingCode );
			
		} else if ( bookingCode == null ) {
			query = entityManager.createQuery( "select e" +
					" from Examination e left join e.appointment" +
					" where e.appointment.acceptanceCode = :acceptanceCode" )
					.setParameter( "acceptanceCode", bookingCode );
			
		} else {
			query = entityManager.createQuery( "select e" +
					" from Examination e left join e.appointment" +
					" where e.appointment.bookingCode = :bookingCode" +
					" and e.appointment.acceptanceCode = :acceptanceCode" )
					.setParameter( "bookingCode", bookingCode )
					.setParameter( "acceptanceCode", acceptanceCode );
		}
		
		List<?> results = query
				.setMaxResults(1)
				.getResultList();
		
		if ( results.size() == 0 ) 
			return null;
		else  if ( results.size() == 1 )
			return (Examination) results.get(0);
		else 
			throw new RuntimeException( "Error: More than one examination returned" ); 
			// può capitare solo nel caso di cancellazione con id_accettazione multiplo 
			// usato per fare più accettazioni in blocco
	}

	@Override
	public Long countByType(ExaminationType type) {
		return (Long) entityManager.createQuery("select count(e) from Examination e where e.type = :type").setParameter("type", type).getSingleResult();
	}
	
	
	private Long getId(Fact fact) {
		return ClassHelper.cast( fact, FactImpl.class ).getId();
	}

	@Override
	public void deleteById( Long id ) {
		if ( id != null ) {
			Examination exam = findById(id);
			entityManager.remove( exam.getAppointment() );
			entityManager.remove( exam );
		}

	}
}
