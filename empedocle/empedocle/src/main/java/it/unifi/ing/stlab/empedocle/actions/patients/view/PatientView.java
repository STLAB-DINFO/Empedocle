package it.unifi.ing.stlab.empedocle.actions.patients.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.actions.health.examination.ExaminationPrint;
import it.unifi.ing.stlab.empedocle.actions.health.examination.ExaminationRowStyleHelper;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.factquery.dao.FactQueryConstructor;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.model.Viewer;

@Named
@ViewScoped
public class PatientView extends Navigator {
	
	//
	// CDI injections
	//
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject 
	protected PatientExaminationFilter examinationFilter;
	
	@Inject
	private ExaminationPrint examinationPrint;

	@Inject
	private FactQueryConstructor queryConstructor;
	
	//
	// HttpParam injections
	//
	@Inject @HttpParam("id")
	private String id;
	
	@Inject @HttpParam("from")
	private String from;
	
	@Inject @HttpParam("eid")
	private String examinationId;
	
	//
	// EJB injections
	//
	@Inject
	private ExaminationDao examinationDao;
	
	@Inject
	private PatientDao patientDao;
	
	@Inject
	private ViewerDao viewerDao;
	
	//
	// Local attributes
	//
	private Patient current;

	// Attributes for list
	private String selection;
	
	// Attribute for merge
	private List<Patient> matchingPatients;
//
	private List<Viewer> factPanels;
	private List<Viewer> reports;


	@PostConstruct
	public void init() {
		current = patientDao.findById( Long.parseLong( id ) );

		initFilter();
		setNavigationStatus( examinationFilter );
		refreshCurrentPage();
		
		initFactPanels();
		initMatchingPatients();
	}

	@Produces @RequestScoped @PatientExaminationResults @Named( "patientExaminationResults" ) 
	public List<Examination> getResults() {
		if ( getItemCount().intValue() == 0 ) 
			return new ArrayList<Examination>();
		return examinationDao.find( examinationFilter,  getOffset(),  getLimit() );
	}
	
	public boolean hasView( Examination examination ){
		if ( examination == null ) return false;
		
		return ( hasDone(examination) || hasConcluded(examination) || hasSuspended( examination ));
	}
	
	public boolean hasReport( Examination examination ){
		if ( examination == null ) return false;
		
		return ( hasDone(examination) || hasConcluded(examination) );
	}
	
	public boolean checkRoleFor( String operation ) {
		
		switch ( operation ) {
		case "edit":
		case "delete":
			return loggedUser.hasRole( RoleType.PATIENT_EDITOR ) || loggedUser.hasRole(RoleType.ADMINISTRATOR);
			
		case "merge":
			return loggedUser.hasRole( RoleType.PATIENT_MERGER );
			
		case "recover":
			return loggedUser.hasRole( RoleType.EXAMINATION_RESCUER );			
			
		default:
			return false;
		}
	}
	
	public boolean hasStart( Examination examination ) {
		if ( examination == null ) return false;
		
		return ExaminationStatus.TODO.equals( examination.getStatus() ) &&
				AppointmentStatus.ACCEPTED.equals( examination.getAppointment().getStatus() );
	}
	public boolean hasModify( Examination examination ) {
		if ( examination == null ) return false;
		
		return ExaminationStatus.DONE.equals( examination.getStatus() );
	}
	public boolean hasResume( Examination examination ) {
		if ( examination == null ) return false;
		
		return ExaminationStatus.SUSPENDED.equals( examination.getStatus() );
	}
	public boolean hasRecover( Examination examination ) {
		if ( examination == null ) return false;
		
		return ExaminationStatus.RUNNING.equals( examination.getStatus() ) && checkRecoverability(examination);
	}

	
	//
	// navigation methods
	//
	public String merge( Long other ) {
		Patient result = patientDao.mergePatients( getCurrent().getId(), other,
				loggedUser.getUser() );

		return "patient-view?faces-redirect=true&from=patient-list&id=" + result.getId();
	}
	
	public String run( Long id ) {
		selection = Long.toString( id );
		conversation.begin();
		return "run";
	}
	
	public void initReports( Long id ) {
		selection = Long.toString( id );
		reports = examinationPrint.initSelectedReports( examinationDao.findById( id ) );
	}
	
	
	//
	// get methods
	//
	public String getId() {
		return id;
	}
	
	public String getSelection() {
		return selection;
	}	
	
	public String getFrom() {
		return from;
	}
	
	public String getExaminationId() {
		return examinationId;
	}
	
	public Patient getCurrent() {
		return current;
	}
	
	public List<Viewer> getReports(){
		return reports;
	}
	
	public String getRowStyleClasses() {
		return ExaminationRowStyleHelper.getRowStyleClasses( getResults() );
	}
	
	public String getRowStyleClass( Examination exam ) {
		return ExaminationRowStyleHelper.getRowStyleClass( exam );
	}
	
	public List<Viewer> getFactPanels() {
		return factPanels;
	}

	public List<Patient> getMatchingPatients() {
		return matchingPatients;
	}
	
	@Override
	public Integer getItemCount() {
		return examinationDao.count( examinationFilter );
	}
	
	//
	// private methods
	//
	private boolean hasDone( Examination examination ) {
		return ExaminationStatus.DONE.equals( examination.getStatus() );
	}
	private boolean hasConcluded( Examination examination ) {
		return ExaminationStatus.CONCLUDED.equals( examination.getStatus() );
	}
	private boolean hasSuspended( Examination examination ) {
		return ExaminationStatus.SUSPENDED.equals( examination.getStatus() );
	}
	
	private Boolean checkRecoverability( Examination examination ) {
		if ( examination.getAuthor() == null )
			return false;
		else 
			return examination.getAuthor().getUserid().equals( loggedUser.getUsername() ) 
					|| checkRoleFor( "recover" );
	}
	
	private void initMatchingPatients() {
		matchingPatients = patientDao.findForMerge( current.getName(), current.getSurname(), current.getId() );
	}
	
	private void initFactPanels() {
		queryConstructor.addAdditionalParams( "pid", Long.parseLong( id ) );
		queryConstructor.addAdditionalParams( "notStatus", FactStatus.REFUSED );
		queryConstructor.addAdditionalParams( "agendas", loggedUser.getAgendas() );
		queryConstructor.addAdditionalParams( "contextStatuses", Arrays.asList( ExaminationStatus.DONE,
																				ExaminationStatus.CONCLUDED ) );
		factPanels = new ArrayList<Viewer>();
		
		//FIXME lo lascio così fino alla decisione di metterlo in un dao
		List<Long> factPanelsId = entityManager.createQuery(
				" select vu.viewer.id from ViewerUse vu"
				+ " where vu.context = :context"
				+ " and vu.qualification.id = :qualificationId ", Long.class )
				.setParameter( "context", ExaminationTypeContext.HIGHLIGHTS )
				.setParameter( "qualificationId", loggedUser.getUserQualification().getId() )
				.getResultList();
		
		for ( Long id : factPanelsId ) {
			factPanels.add( viewerDao.fetchById( id ) );
		}
	}
	
	private void initFilter() {
		examinationFilter.setPatientId( Long.parseLong( id ) );
	}
}
