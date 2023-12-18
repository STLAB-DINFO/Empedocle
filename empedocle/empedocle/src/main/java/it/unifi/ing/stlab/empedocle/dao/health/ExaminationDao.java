package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationOperation;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

@Local
public interface ExaminationDao {

	public int count( ExaminationQueryBuilder builder );
	public Long countByType(ExaminationType type);
	public int countPatientHistory(Long patientId, Long examFromId,
			Set<ExaminationStatus> statuses, Set<Agenda> agendas);
	public Long countUserExaminationsByStatus(String userid, ExaminationStatus status, Date start, Date end);
	
	public boolean hasPatientHistory(Long patientId);
	public boolean hasPatientHistory(Long patientId, Set<ExaminationStatus> statuses, Set<Agenda> agendas);
	
	public Examination findById( Long id );
	public Examination findByAppointmentCodes(String bookingCode, String acceptanceCode);
	public List<Examination> find( ExaminationQueryBuilder builder, int offset, int limit );
	public List<Examination> findPatientLastExams(Long patientId, Long lastExamId, int numExams);
	public List<Examination> findPatientHistory(Long patientId,
			Long examFromId, Set<ExaminationStatus> statuses,
			Set<Agenda> agendas, int offset, int limit);
	
	public ExaminationDetails fetchById( Long examinationId, Long qualificationId, ExaminationTypeContext context  );
	public ExaminationDetails fetchByExaminationViewer( Long examinationId, Long qualificationId, Long viewerId  );

	public boolean hasPermission( Long examinationId, Long qualificationId, ExaminationOperation operation );
	
	public void update( Examination e );
	public void save( Examination e );
	public void deleteById( Long id ) ;
	
	public Fact resume(Fact f, Patient p);
}
