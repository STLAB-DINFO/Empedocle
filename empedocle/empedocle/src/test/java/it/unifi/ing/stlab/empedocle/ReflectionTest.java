package it.unifi.ing.stlab.empedocle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;

import it.unifi.ing.stlab.empedocle.actions.health.examination.RecurrentFactHelper;
import it.unifi.ing.stlab.empedocle.actions.util.GarbageCollectorHelper;
import it.unifi.ing.stlab.empedocle.actions.util.GarbageCollectorHelperBean;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDaoBean;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDao;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationTypeDaoBean;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationDetails;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactVisitor;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.TypeVisitor;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.test.TimeTracker;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.dao.UserDaoBean;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.Paragraph;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.FileUpload;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputLink;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

// TODO Eseguire i seguenti tests (1000 ripetizioni):
// 1. start-end su visita todo oculistica e cardio
// 2. resume-suspend su visita sospesa (vuota o con alcuni campi riempiti???) oculistica e cardio
// 3. search-view su visita done/suspended (vuota o con alcuni campi riempiti???) oculistica e cardio


//@RunWith(Parameterized.class)
public class ReflectionTest extends JpaTest {

	private UserDao userDao;
	private FactDao factDao;
	private TypeDao typeDao;
	private ViewerDao viewerDao;
	private ExaminationDao examinationDao;
	private ExaminationTypeDao examinationTypeDao;
	private GarbageCollectorHelper garbageCollector;
		
	@Parameters
	public static List<Object[]> data() {
	    return Arrays.asList(new Object[2][0]);
	}
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_reflection" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();

		userDao = new UserDaoBean();
		FieldUtils.assignField( userDao, "entityManager", entityManager );
		
		factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );
		
		typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );
		
		viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );

		examinationDao = new ExaminationDaoBean();
		FieldUtils.assignField( examinationDao, "entityManager", entityManager );
		FieldUtils.assignField( examinationDao, "factDao", factDao );
		FieldUtils.assignField( examinationDao, "typeDao", typeDao );
		FieldUtils.assignField( examinationDao, "viewerDao", viewerDao );
		
		examinationTypeDao = new ExaminationTypeDaoBean();
		FieldUtils.assignField( examinationDao, "entityManager", entityManager );
		
		garbageCollector = new GarbageCollectorHelperBean();
		FieldUtils.assignField( examinationDao, "entityManager", entityManager );
	}

//	@Test
	public void testStartEnd() {
		TimeTracker timeTracker = new TimeTracker( "Start-End" );
		
		timeTracker.start();
		// Read
		Examination examination = examinationDao.findById( new Long( 174350  ));
		
		// Start
		ExaminationType examinationType = examinationTypeDao.findByExaminationId( examination.getId() );
		User user = userDao.findByUsername( "administrator" );
		
		Type type = typeDao.fetchById( examinationType.getType().getId() );
		FactFactoryVisitor factFactory = new FactFactoryVisitor( user, new Time( new Date( System.currentTimeMillis())));
		type.accept( factFactory );
		
		Fact fact = factFactory.getResult();
		AssignContextVisitor assignContext = new AssignContextVisitor( examination );
		fact.accept( assignContext );
		
		FactDefaultInitializerVisitor assignDefault = new FactDefaultInitializerVisitor();
		fact.accept( assignDefault );

		RecurrentFactHelper recurrentHelper = new RecurrentFactHelper(examinationDao);
		recurrentHelper.resumeRecurrentFacts(fact);
		garbageCollector.flush();

		examination.setStatus( ExaminationStatus.RUNNING );
		examination.setType( examinationType );
		examination.setLastUpdate( new Date( System.currentTimeMillis() ));
		examination.setAuthor( user );

		factDao.save( fact );
		entityManager.getTransaction().commit();

		// End
		entityManager.getTransaction().begin();
		
		Date date = new Date( System.currentTimeMillis() );
		
		examination.setStatus( ExaminationStatus.DONE );
		examination.setWasDone( true );
		examination.setLastUpdate( date );
		FactManager factManager = new FactManager();
		factManager.purge( (FactImpl)fact );
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));

		entityManager.getTransaction().commit();
		
		timeTracker.stop();
		printResult( timeTracker );
	}	
	
//	@Test
	public void testResumeSuspend() {
		TimeTracker timeTracker = new TimeTracker( "Resume-Suspend" );
		
		timeTracker.start();
		// Read
		Examination examination = examinationDao.findById( new Long( 174350  ));
		User user = userDao.findByUsername( "administrator" );
		Date date = new Date( System.currentTimeMillis() );
		Time time = new Time( date );

		// Resume
		entityManager.getTransaction().begin();
		Fact source = factDao.findByContextId( examination.getId(), examination.getType().getType().getId() );
		factDao.fetchById( ((FactImpl)source).getId() );

		FactManager factManager = new FactManager();
		Fact dest = factManager.modify( user, time, (FactImpl)source );

		examination.setStatus( ExaminationStatus.RUNNING );
		examination.setLastUpdate( date );
		examination.setAuthor( user );

		factDao.save( dest );
		entityManager.getTransaction().commit();

		// Suspend
		entityManager.getTransaction().begin();

		examination.setStatus( ExaminationStatus.SUSPENDED );
		examination.setLastUpdate( date );
		factManager.purge( (FactImpl)dest );
		GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
		entityManager.getTransaction().commit();
		
		timeTracker.stop();
		printResult( timeTracker );
	}
	
//	@Test
	public void testSearchView() {
		TimeTracker timeTracker = new TimeTracker( "Search-View" );
		
		timeTracker.start();

		ExaminationDetails examinationDetails = examinationDao.fetchById( new Long( 174350  ), new Long( 1  ), ExaminationTypeContext.EDIT );
	
		examinationDetails.getFact().accept( new ReflectionFactVisitor( new ReflectionTypeVisitor()));
		examinationDetails.getViewer().accept( new ReflectionViewerVisitor());

		timeTracker.stop();
		printResult( timeTracker );
	}
	
	private void printResult( TimeTracker t) {
		PrintWriter writer;
		try {
			writer = new PrintWriter( new FileOutputStream( new File( t.getMethod() + ".txt"), true ));
			writer.println( Long.toString( t.getDuration() ));
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}


class ReflectionFactVisitor implements FactVisitor {

	private ReflectionTypeVisitor typeVisitor;
	
	
	public ReflectionFactVisitor(ReflectionTypeVisitor typeVisitor) {
		super();
		this.typeVisitor = typeVisitor;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		visitType( fact );
		fact.getText();
		
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		visitType( fact );
		if ( fact.getQuantity() != null ) {
			fact.getQuantity().getUnit();
		}
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		visitType( fact );
		if ( fact.getPhenomenon() != null ) {
			fact.getPhenomenon().getName();
		}
	}

	@Override
	public void visitTemporal(TemporalFact fact) {
		visitType( fact );
		fact.getDate();
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		visitType( fact );

		for ( FactLink link : fact.listActiveLinks() ) {
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
	
	private void visitType( Fact fact ) {
		if ( fact.getType() != null ) {
			fact.getType().accept( typeVisitor );
		}
	}
}

class ReflectionTypeVisitor implements TypeVisitor {

	@Override
	public void visitTextualType(TextualType type) {
		type.getName();
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		type.getName();
		
		for( Phenomenon ph : type.listPhenomena()) {
			ph.getName();
		}
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		type.getName();
		type.getQueryDef();
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		type.getName();
		
		for ( UnitUse uu : type.listUnits()) {
			uu.getUnit();
		}
	}

	@Override
	public void visitTemporalType(TemporalType type) {
		type.getName();
		type.getType();
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		type.getName();
		
		for ( TypeLink link : type.listChildren() ) {
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
	
}

class ReflectionViewerVisitor implements ViewerVisitor {

	@Override
	public void visitInputText(InputText viewer) {
		viewer.getName();
	}

	@Override
	public void visitInputTemporal(InputTemporal viewer) {
		viewer.getName();
	}

	@Override
	public void visitTextArea(TextArea viewer) {
		viewer.getName();
	}

	@Override
	public void visitCombo(Combo viewer) {
		viewer.getName();
	}

	@Override
	public void visitSuggestion(Suggestion viewer) {
		viewer.getName();
	}

	@Override
	public void visitInputList(InputList viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}
	
	@Override
	public void visitFileUpload(FileUpload viewer) {
		viewer.getName();
	}

	@Override
	public void visitLabel(Label viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputType(OutputType viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputValue(OutputValue viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputMeasurementUnit(OutputMeasurementUnit viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputPath(OutputPath viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputList(OutputList viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitOutputField(OutputField viewer) {
		viewer.getName();
	}

	@Override
	public void visitOutputImage(OutputImage viewer) {
		viewer.getName();
	}
	
	@Override
	public void visitOutputLink(OutputLink viewer) {
		viewer.getName();
	}	

	@Override
	public void visitGrid(Grid viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitFactPanel(FactPanel viewer) {
		if ( viewer.getQuery() != null ) {
			viewer.getQuery().getName();
		}
		visitViewerChildren(viewer);
	}

	@Override
	public void visitBox(Box viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitConditionalPanel(ConditionalPanel viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitTabbedPanel(TabbedPanel viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitReport(Report viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}

	@Override
	public void visitViewerCustom(ViewerCustom viewer) {
		viewer.getName();
		visitViewerChildren(viewer);
	}
	
	private void visitViewerChildren(Viewer viewer) {
		for ( ViewerLink link : viewer.listChildren() ) {
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}

	@Override
	public void visitParagraph( Paragraph viewer ) {
		viewer.getName();
		visitViewerChildren(viewer);		
	}
}
