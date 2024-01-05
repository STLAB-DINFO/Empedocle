package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ExaminationTypeDao {

	int count(ExaminationTypeQueryBuilder builder);
	List<ExaminationType> find(ExaminationTypeQueryBuilder builder, int offset, int limit);
	ExaminationType findById(Long id);

	void delete(Long id);
	boolean isUsed(Long id);
	
	ExaminationType findByExaminationId(Long id);
	List<Viewer> findAssociatedViewer(Long examTypeId, Long qualificationId, ExaminationTypeContext context);
	
	List<ExaminationType> findAll();
	List<ExaminationType> findWithAgendas();
	List<ExaminationType> findWithAgendas(Long excludeId);
	
	void update(ExaminationType current);
	void save(ExaminationType current);
	
}
