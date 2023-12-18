package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ExaminationTypeDao {

	public int count( ExaminationTypeQueryBuilder builder );
	public List<ExaminationType> find( ExaminationTypeQueryBuilder builder, int offset, int limit );
	public ExaminationType findById( Long id );

	public void delete( Long id );
	public boolean isUsed( Long id );
	
	public ExaminationType findByExaminationId( Long id );
	public List<Viewer> findAssociatedViewer( Long examTypeId, Long qualificationId, ExaminationTypeContext context );
	
	public List<ExaminationType> findAll();
	public List<ExaminationType> findWithAgendas( );
	public List<ExaminationType> findWithAgendas( Long excludeId );
	
	public void update( ExaminationType current );
	public void save( ExaminationType current );
	
}
