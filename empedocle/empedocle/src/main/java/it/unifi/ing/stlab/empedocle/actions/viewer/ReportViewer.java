package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ReportViewer {

	@Inject @HttpParam("eid")	
	private String examinationId;
	
	@Inject @HttpParam("vid")	
	private String viewerId;
	
	@Inject
	private ExaminationDao examinationDao;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private UserDao userDao;
	
	private ExaminationDetails examDetails;
	
	@PostConstruct
	public void init(){
		examDetails = examinationDao.fetchByExaminationViewer( Long.parseLong( examinationId ), getQualificationId(), Long.parseLong( viewerId ));

	}
	
	public boolean isDisplayable(){
		return examDetails != null;
	}
	
	public Fact getFact(){
		if(examDetails == null)
			return null;
		
		return examDetails.getFact();
	}
	
	public Viewer getViewer(){
		if(examDetails == null)
			return null;
		
		return examDetails.getViewer();
	}
	
	private Long getQualificationId() {
		Set<Qualification> result = userDao.listUserQualifications(loggedUser.getUser().getId());
		
		if ( result.size() != 1 ) 
			throw new IllegalStateException( "number of qualifications != 1" );
		
		return result.iterator().next().getId();
	}
	
}
