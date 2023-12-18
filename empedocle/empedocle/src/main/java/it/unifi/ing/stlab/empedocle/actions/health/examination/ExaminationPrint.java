package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.view.model.Viewer;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Dependent
public class ExaminationPrint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Viewer> reports;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private ExaminationTypeDao examinationTypeDao;
	
	public Boolean moreThanOneReport(Examination examination) {
		return getReports(examination).size() > 1;
	}
	
	public List<Viewer> getReports(Examination examination){
		if(reports == null)
			initSelectedReports(examination);
		
		return this.reports;
	}
	
	public List<Viewer> initSelectedReports(Examination examination) {
		reports = examinationTypeDao.findAssociatedViewer(examination.getType().getId(),
							loggedUser.getUserQualification().getId(),
							ExaminationTypeContext.REPORT);
		
		return reports;

	}
	
	public void clear(){
		reports = null;
	}

}
