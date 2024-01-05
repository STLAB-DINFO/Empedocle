package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.AppointmentStatus;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.navigation.Navigator;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.users.model.RoleType;
import it.unifi.ing.stlab.view.model.Viewer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@RequestScoped
public class ExaminationList extends Navigator {

	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	protected ExaminationFilter examinationFilter;
	
	@Inject
	private ExaminationPrint examinationPrint;
	
	//
	// EJB injections
	//
	@Inject
	private ExaminationDao examinationDao;
	
	@Inject
	private PatientDao patientDao;
	
	//
	// Local attributes
	//
	private String selection;
	private String patientId;
	private List<Examination> examinationResults;
	private List<Viewer> selectedReports;
	private Integer itemCount;
	
	@PostConstruct
	public void init() {
		setNavigationStatus( examinationFilter );
		refreshCurrentPage();
	}
	
	@Produces @Named( "examinationResults" ) 
	public List<Examination> getResults() {
		if ( examinationResults == null ) {
			initExaminationResults();
		}
		return examinationResults;
	}
	
	public void initSelectedReports(Examination examination) {
		selection = examination.getId().toString();
		selectedReports = examinationPrint.initSelectedReports(examinationDao.findById(examination.getId()));
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
	public boolean hasConcluded( Examination examination ) {
		if ( examination == null ) return false;
		
		return ExaminationStatus.CONCLUDED.equals( examination.getStatus() );
	}
	public boolean hasReport( Examination examination ){
		if ( examination == null ) return false;
		
		return ( hasModify(examination) || hasConcluded(examination) ); //|| hasResume(examination) );
	}
	
	public String getRowStyleClasses() {
		return ExaminationRowStyleHelper.getRowStyleClasses( getResults() );
	}
	
	public String getPopupContent(Examination e) {
		StringBuilder sb = new StringBuilder(e.getAppointment().getAgenda().toString());
		
		if( !e.getStatus().equals(ExaminationStatus.TODO) ) {
			sb.append(" ")
				.append("Autore: ")
				.append(e.getAuthor().getName())
				.append(" ")
				.append(e.getAuthor().getSurname());
		}
		
		return sb.toString();
	}
	
	public boolean isRemovable( Examination ex ) {
		return ex.getStatus().equals(ExaminationStatus.TODO);
	}

	public Boolean checkHistory( Long pid ){
		Set<Agenda> agendas = loggedUser.getAgendas();
		
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>(
				Arrays.asList(
						ExaminationStatus.SUSPENDED,
						ExaminationStatus.DONE, 
						ExaminationStatus.CONCLUDED));
		
		return examinationDao.hasPatientHistory( pid, statuses, agendas );
	}
	
	public boolean checkRoleFor( String operation ) {
		
		switch ( operation ) {
		case "add-new":
		case "edit":
		case "delete":
			return loggedUser.hasRole( RoleType.EXAMINATION_EDITOR );
		case "recover":
			return loggedUser.hasRole( RoleType.EXAMINATION_RESCUER );
			
		default:
			return false;
		}
	}
	
	
	//
	// navigation methods
	//
	public String addNew() {
		conversation.begin();
		return "add-new";
	}	
	
	public String run( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "run";
	}	
	
	public String edit( Long id ) {
		selection = id.toString();
		conversation.begin();
		return "edit";
	}
	
	public String delete( Long id ) {
		examinationDao.deleteById( id );
		
		facesContext.addMessage(null,
                new FacesMessage(
                		FacesMessage.SEVERITY_INFO, 
                		"Visita cancellata con successo!", null));
		facesContext.getExternalContext().getFlash().setKeepMessages(true);		
		
		return "delete";
	}
	
	public String history( Long pid ){
		patientId = Long.toString( pid );
		
		return "history";
	}
	
	
	//
	// get methods
	//
	
	public Patient getLastPatientVersion( Long pid ) {
		return patientDao.findLastVersionById( pid );
	}
	
	@Override
	public Integer getItemCount() {
		if ( itemCount == null ) {
			itemCount = examinationDao.count( examinationFilter );
		}
		return itemCount;
	}
	
	public String getSelection() {
		return selection;
	}
	
	public String getPatientId() {
		return patientId;
	}
	
	public List<Viewer> getSelectedReports(){
		if(selection != null && !"".equals(selection.trim())) {
			selectedReports = examinationPrint.getReports(examinationDao.findById(Long.parseLong(selection)));
		}
		
		return selectedReports;
	}
	
	//
	// private methods
	//
	private Boolean checkRecoverability(Examination examination) {
		if ( examination.getAuthor() == null )
			return false;
		else 
			return examination.getAuthor().getUserid().equals( loggedUser.getUsername() ) 
					|| checkRoleFor( "recover" );
	}
	
	private void initExaminationResults() {
		if ( getItemCount().intValue() == 0 ) { 
			examinationResults =  new ArrayList<Examination>();
		} else {
			examinationResults = examinationDao.find( examinationFilter,  getOffset(),  getLimit() );
		}
	}
}
