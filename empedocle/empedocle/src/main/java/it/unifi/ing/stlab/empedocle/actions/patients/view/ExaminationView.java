package it.unifi.ing.stlab.empedocle.actions.patients.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.actions.health.examination.ExaminationRowStyleHelper;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;

@Named
@ViewScoped
public class ExaminationView implements Serializable {

	private static final long serialVersionUID = -1363640151085653007L;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;
	
	@Inject
	@HttpParam( "pid" )
	private String pid;
	
	@Inject
	@HttpParam( "eid" )
	private String eid;
	
	@Inject @HttpParam("from")
	private String from;
	
	@Inject
	private LoggedUser loggedUser;

	//
	// EJB injections
	//
	@Inject
	private ExaminationDao examinationDao;

	//
	// Local attributes
	//
	private ExaminationDetails current;

	@PostConstruct
	public void init() {
		current = examinationDao.fetchById( 
				Long.parseLong( id ),
				loggedUser.getUserQualification().getId(), 
				ExaminationTypeContext.VIEW );
	}
	
	public String getStyleClass( Examination examination ) {
		return ExaminationRowStyleHelper.getRowStyleClass( examination );
	}
	
	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}
	
	public String getFrom() {
		return from;
	}
	
	public ExaminationDetails getCurrent() {
		return current;
	}
}
