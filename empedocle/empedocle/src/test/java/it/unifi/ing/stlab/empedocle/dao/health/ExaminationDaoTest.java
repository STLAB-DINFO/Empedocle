package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDaoBean;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.view.dao.ViewerDao;
import it.unifi.ing.stlab.view.dao.ViewerDaoBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

//XXX check
@Ignore
public class ExaminationDaoTest extends JpaTest {

	protected ExaminationDao examinationDao;
	protected AgendaDao agendaDao;
	 
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_mysql" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		FactDao factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );

		ViewerDao viewerDao = new ViewerDaoBean();
		FieldUtils.assignField( viewerDao, "entityManager", entityManager );
		
		examinationDao = new ExaminationDaoBean();
		FieldUtils.assignField( examinationDao, "entityManager", entityManager );
		FieldUtils.assignField( examinationDao, "factDao", factDao );
		FieldUtils.assignField( examinationDao, "viewerDao", viewerDao );
		
		agendaDao = new AgendaDaoBean();
		FieldUtils.assignField( agendaDao, "entityManager", entityManager );
		
		entityManager.clear();
	}
	
	@Test
	public void testFindPatientHistoryNullStatusesAndAgendas() {
		List<Examination> exams = examinationDao.findPatientHistory( new Long(83695), new Long(181896), null, null, 0, 5 );
		assertEquals(0, exams.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPatientHistoryNullStatuses() {
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "IMPORTCARDIO" ));
		
		examinationDao.findPatientHistory( new Long(83695), new Long(181896), null, agendas, 0, 5 );
	}
	
	@Test
	public void testFindPatientHistoryNullAgendas() {
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>();
		statuses.add( ExaminationStatus.CONCLUDED );
		statuses.add( ExaminationStatus.DONE );
		
		List<Examination> exams = examinationDao.findPatientHistory( new Long(83695), new Long(181896), statuses, null, 0, 5 );
		assertEquals(0, exams.size());
	}		
	
	@Test
	public void testFindPatientHistory() {
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>();
		statuses.add( ExaminationStatus.CONCLUDED );
		statuses.add( ExaminationStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		List<Examination> exams = examinationDao.findPatientHistory( new Long(83695), new Long(181896), statuses, agendas, 0, 5 );
		assertEquals( 1, exams.size() );
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPatientHistoryFail1() {
		examinationDao.findPatientHistory( null, new Long(181896), null, null, 0, 5 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindPatientHistoryFail2() {
		examinationDao.findPatientHistory( new Long(83695), null, null, null, 0, 5 );
	}
	
	@Test
	public void testHasPatientHistory() {
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>();
		statuses.add( ExaminationStatus.CONCLUDED );
		statuses.add( ExaminationStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		boolean result = examinationDao.hasPatientHistory( new Long(83695), statuses, agendas);
		assertTrue(result);
	}
	
	@Test
	public void testCountPatientHistory() {
		Set<ExaminationStatus> statuses = new HashSet<ExaminationStatus>();
		statuses.add( ExaminationStatus.CONCLUDED );
		statuses.add( ExaminationStatus.DONE );
		
		Set<Agenda> agendas = new HashSet<Agenda>();
		agendas.add( agendaDao.findByCode( "TESTCARDIO" ));
		
		int result = examinationDao.countPatientHistory( new Long(83695), new Long(181896), statuses, agendas );
		assertEquals( 1, result );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountPatientHistoryFail1() {
		examinationDao.countPatientHistory( null, new Long(181896), null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountPatientHistoryFail2() {
		examinationDao.countPatientHistory( new Long(82727), null, null, null);
	}
	
}
