package it.unifi.ing.stlab.empedocle.actions.health.examination;

import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.test.PersistenceTest;

import javax.transaction.UserTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExaminationRunningTest extends PersistenceTest {

	// TODO ExaminationRunning tests
	
	@Mock private UserTransaction utx;
//	@Mock private StaffD
	
	private ExaminationRunning examinationRunning;
	
	private Examination examination;
	
	public void setUp() throws Exception {
		super.setUp();
		examinationRunning = new ExaminationRunning();
		examinationRunning.setSummary(true);
		examination = ExaminationFactory.createExamination();
		examination.setAppointment(AppointmentFactory.createAppointment());
		examination.setStatus(ExaminationStatus.DONE);
		examination.setType(ExaminationTypeFactory.createExaminationType());
	}
	
	@Test
	public void testReopen() {
		
	}
	
//	public String reOpen() {
//		summary = false;
//		initExaminationDetails();
//		getExaminationDetails().getExamination().setStatus( ExaminationStatus.RUNNING );
//		getExaminationDetails().getExamination().setLastUpdate( new Date( System.currentTimeMillis() ) );
//		return "edit";
//	}
	
}
