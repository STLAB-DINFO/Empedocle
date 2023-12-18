package it.unifi.ing.stlab.empedocle.actions.health.examination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletException;
import javax.transaction.UserTransaction;

import it.unifi.ing.stlab.empedocle.actions.patients.view.PatientExaminationResults;
import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import org.apache.log4j.Logger;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.actions.util.GarbageCollectorHelper;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationOperation;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.empedocle.security.LoginBean;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactGarbageVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.view.model.Viewer;


@Named
@Stateful
@ConversationScoped
@TransactionManagement( TransactionManagementType.BEAN )
public class ExaminationRunning implements Serializable {

	private static final long serialVersionUID = -4532411287125219807L;
	
	private ExaminationDetails examinationDetails;
	private boolean summary;
		
	private Patient lastPatientVersion;
	private List<Examination> patientLastExams;

	private List<Viewer> examReports;
	private String selection;
	
	//
	// CDI injections
	//
	@Inject
	private FacesContext facesContext;	
	

	@Inject
	private Conversation conversation;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private ExaminationPrint examinationPrint;	
	
	//
	// EJB injections
	//
	
	@PersistenceContext( type = PersistenceContextType.EXTENDED )
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction utx;
	
	@EJB
	private ExaminationTypeDao examinationTypeDao;

	@EJB
	private ExaminationDao examinationDao;
	
	@EJB
	private TypeDao typeDao;

	@EJB
	private FactDao factDao;

	@EJB
	private UserDao userDao;
	
	@EJB
	private PatientDao patientDao;

	@EJB
	private GarbageCollectorHelper garbageCollector;
	
	private final Logger logger = Logger.getLogger(ExaminationRunning.class);

	private Long tmpID;

	private Boolean dateFreeVisit = false;
	private Date visitDate;

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
	// Initialization methods
	//
	
	@PostConstruct
	public void init(){
		beginConversation();

		examinationDetails = null;
		summary = false;
		
		if ( id == null )
			throw new IllegalArgumentException( "Visita non specificata" );

		try {
			utx.begin();


			Examination examination = examinationDao.findById( Long.parseLong( id ));

			if ( examination == null ) {
				utx.rollback();
				throw new IllegalArgumentException( "Visita non trovata" );
			}
			
			initLastPatientVersion( examination );

			if(patientLastExams == null){
				patientLastExams = examinationDao.findPatientLastExams(lastPatientVersion.getId(), Long.parseLong( id ), 10);
			}

			/*TEST*/
			//caso in cui la visita è con Data libera, da specificare per completare l'erogazione
			if(examination.getAppointment().getDate() == null){
				setDateFreeVisit(true);
				visitDate = new Date();
			}

			/*FINE TEST*/

			switch ( examination.getStatus() ) {
			case TODO:
				startExamination( examination );
				break;
			case DONE:
				modifyExamination( examination );
				break;
			case SUSPENDED:
				resumeExamination( examination );
				break;
			case RUNNING:
				recoverExamination( examination );
				break;
			default:
				utx.rollback();
				throw new IllegalArgumentException( "Visita non editabile" );
			}
			
			utx.commit();
			
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}


	//
	// Public interface
	//

	/*@RequestScoped
	public List<Examination> getResults() {
		return examinationDao.find( examinationFilter,  0,  10 );
	}

*/

	public List<Examination> getPatientLastExams(){
		return this.patientLastExams;
	}

	public void initReports( Long id ) {
		selection = Long.toString( id );
		examReports = examinationPrint.initSelectedReports( examinationDao.findById( id ) );
	}

	public String getSelection() {
		return selection;
	}
	public List<Viewer> getExamReports(Long id) {
		tmpID = id;
		if (examReports == null || examReports.size() == 0) {
			initReports( id );
		}
		return examReports;
	}


	//
	// Private methods
	//

	private void initLastPatientVersion( Examination examination ) {
		lastPatientVersion = patientDao
				.findLastVersionById( examination.getAppointment().getPatient().getId() );		
	}

	private void beginConversation() {
		if(conversation.isTransient()) {
			conversation.begin();
		}
	}

	private boolean hasDone( Examination examination ) {
		return ExaminationStatus.DONE.equals( examination.getStatus() );
	}
	private boolean hasConcluded( Examination examination ) {
		return ExaminationStatus.CONCLUDED.equals( examination.getStatus() );
	}
	private boolean hasSuspended( Examination examination ) {
		return ExaminationStatus.SUSPENDED.equals( examination.getStatus() );
	}
	
	private void startExamination( Examination examination ) {
		try{
			ExaminationType examinationType = examinationTypeDao.findByExaminationId( examination.getId() );
			User user = userDao.findByUsername( loggedUser.getUsername() );

			Type type = typeDao.fetchById( examinationType.getType().getId() );
			FactFactoryVisitor factFactory = new FactFactoryVisitor( user, new Time( new Date( System.currentTimeMillis())));
			type.accept( factFactory );

			Fact fact = factFactory.getResult();
			AssignContextVisitor assignContext = new AssignContextVisitor( examination );
			fact.accept( assignContext );

			FactDefaultInitializerVisitor assignDefault = new FactDefaultInitializerVisitor();
			fact.accept( assignDefault );

			// recupero osservazioni ricorrenti
			RecurrentFactHelper recurrentHelper = new RecurrentFactHelper(examinationDao);
			recurrentHelper.resumeRecurrentFacts(fact);
			garbageCollector.flush();

			examination.setStatus( ExaminationStatus.RUNNING );
			examination.setType( examinationType );
			examination.setLastUpdate( new Date( System.currentTimeMillis() ));
			examination.setAuthor( user );

			factDao.save( fact );
		}catch(Exception e){ // quando la creazione del fact fallisce, si cancellano anche l'Appointment e la Examination relativi
			examinationDao.deleteById(examination.getId());
		}

	}

	public boolean hasView( Examination examination ){
		if ( examination == null ) return false;

		return ( hasDone(examination) || hasConcluded(examination) || hasSuspended( examination ));
	}


	private void modifyExamination( Examination examination ) {
		User user = userDao.findByUsername( loggedUser.getUsername() );
		Date date = new Date( System.currentTimeMillis() );
		Time time = new Time( date );
		
		Fact source = factDao.findByContextId( examination.getId(), examination.getType().getType().getId() );
		Long id = ClassHelper.cast( source, FactImpl.class ).getId();
		factDao.fetchById( id );

		FactManager factManager = new FactManager();
		Fact dest = factManager.modify( user, time, ClassHelper.cast( source, FactImpl.class ) );

		examination.setStatus( ExaminationStatus.RUNNING );
		examination.setLastUpdate( date );
		examination.setAuthor( user );

		factDao.save( dest );
		
	}

	
	private void resumeExamination( Examination examination ) {
		modifyExamination( examination );
	}
	private void recoverExamination( Examination examination ) {
	}
	
	
	//
	// Persisting data on "partial submit"
	//
	public void persistData() {
		try {
			utx.begin();
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	//
	// Examination details
	//
	
	public ExaminationDetails getExaminationDetails() {
		if ( examinationDetails == null ) {
			initExaminationDetails();
		}
		return examinationDetails;
	}
	
	private void initExaminationDetails() {
		try {
			utx.begin();
			
			examinationDetails = examinationDao.fetchById( 
					Long.parseLong( id ), 
					loggedUser.getUserQualification().getId(), 
					( summary ? ExaminationTypeContext.VIEW : ExaminationTypeContext.EDIT ));
			
			entityManager.lock(	examinationDetails.getExamination(), LockModeType.OPTIMISTIC );
			
			utx.commit();
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}
	
	
	//
	// Reports
	//
	
	public List<Viewer> getReports(){
		return examReports;
	}
	
	
	//
	// Summary
	//
	
	public boolean isSummary() {
		return summary;
	}
	public void setSummary(boolean summary) {
		this.summary = summary;
	}

	
	//
	// Permission Check Methods
	//
	
	public boolean checkEndAuthorization(){
		if( loggedUser == null ) {
			logger.info("loggedUser is null!");
		}
		if( id == null ) {
			logger.info("examinationId is null!");
		}
			
		return examinationDao.hasPermission( new Long( id ), 
									loggedUser.getUserQualification().getId(), 
									ExaminationOperation.END_EXAMINATION );
	}
	
	//
	// Ending methods
	//
	
	public String suspend(){
		Examination examination = getExaminationDetails().getExamination();
		Fact fact = getExaminationDetails().getFact();
		Date date = new Date( System.currentTimeMillis() );
		
		try {
			utx.begin();
			
			examination.setStatus( ExaminationStatus.SUSPENDED );
			examination.setLastUpdate( date );
			FactManager factManager = new FactManager();
			factManager.purge( ClassHelper.cast( fact, FactImpl.class ) );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
			
			utx.commit();

			conversation.end();
			
			return "patient-list";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	
	public String cancel(){
		Examination examination = getExaminationDetails().getExamination();
		
		try {
			utx.begin();
			
			examination.setStatus( ExaminationStatus.TODO );
			Fact toRemove = examinationDetails.getFact();
			toRemove.accept( new FactGarbageVisitor() );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ) );
			
			utx.commit();

			conversation.end();
			
			return "patient-list";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	
	public String end(){
		Examination examination = getExaminationDetails().getExamination();



		Fact fact = getExaminationDetails().getFact();
		Date date = new Date( System.currentTimeMillis() );
		
		try {
			utx.begin();
			
			examination.setStatus( ExaminationStatus.DONE );
			examination.setWasDone( true );
			examination.setLastUpdate( date );
			FactManager factManager = new FactManager();
			Fact purged = factManager.purge( ClassHelper.cast( fact, FactImpl.class ) );
			GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));

			if(dateFreeVisit){
				if(visitDate != null){
					examination.getAppointment().setDate(visitDate);
					examination.setLastUpdate(visitDate);
					/*String tmpDate = DateUtils.getString( examination.getAppointment().getDate(), "yyyyMMddHHmmss" );
					String bookingCode = examination.getAppointment().getBookingCode().replaceAll("__", tmpDate );
					String acceptanceCode = examination.getAppointment().getAcceptanceCode().replaceAll("__", tmpDate );
					examination.getAppointment().setBookingCode(bookingCode);
					examination.getAppointment().setAcceptanceCode(acceptanceCode);*/
				}
			}
			
			utx.commit();
			
			summary = true;
			examinationDetails = null;
			examinationPrint.clear();
			initReports(Long.parseLong(id));
			
			return "summary";
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	

	public String close() {
		conversation.end();
		return "patient-list";
	}
	
	public String switchUser() {
		try {
			suspend();
			loginBean.logout();
			return "switch-user";
		} catch (ServletException e) {
			throw new RuntimeException();
		}
	}
	
	// TODO testare
	public String reOpen() {
		summary = false;
		initExaminationDetails();
		this.setDateFreeVisit(false);
		getExaminationDetails().getExamination().setStatus( ExaminationStatus.RUNNING );
		getExaminationDetails().getExamination().setLastUpdate( new Date( System.currentTimeMillis() ) );
		return "run";
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getRowStyleClasses( List<Examination> e ) {
		return ExaminationRowStyleHelper.getRowStyleClasses( e );
	}
	
	//XXX metodi di servizio per inputList e immagini svg - per ora si lasciano qua
	public void addChildren(TypeLink tl, Fact f) {
		try {
			utx.begin();
			
			factDao.addChildren(tl, f, userDao.findByUsername( loggedUser.getUsername() ), new Time( new Date( System.currentTimeMillis() )));
			
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void removeChildren(FactLink fl) {
		try {
			utx.begin();
			
			factDao.removeChildren(fl);
			garbageCollector.flush();
			
			utx.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}	


	public boolean hasReport( Examination examination ){
		if ( examination == null ) return false;
		
		return ( ExaminationStatus.DONE.equals( examination.getStatus() ) 
				|| ExaminationStatus.CONCLUDED.equals( examination.getStatus() ) );
	}

	public boolean has2Report( Examination examination ){
		if ( examination == null ) return false;

		if(!( ExaminationStatus.DONE.equals( examination.getStatus() )|| ExaminationStatus.CONCLUDED.equals( examination.getStatus() ) )){
			return false;
		}

		if (examinationTypeDao.findAssociatedViewer(examination.getType().getId(), loggedUser.getUserQualification().getId(), ExaminationTypeContext.REPORT).size()>1) {
			return true;
		}

		return false;

	}

	
	private void message( Severity severityInfo, String message, boolean keepMessages ) {
		facesContext.addMessage( null, new FacesMessage( severityInfo, message, null ) );
		facesContext.getExternalContext().getFlash().setKeepMessages( keepMessages );
	}
	
	public Patient getLastPatientVersion() {
		return lastPatientVersion;
	}

	public String getExaminationId() {
		return this.id;
	}

	public Boolean getDateFreeVisit() {
		return dateFreeVisit;
	}

	public void setDateFreeVisit(Boolean dateFreeVisit) {
		this.dateFreeVisit = dateFreeVisit;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
