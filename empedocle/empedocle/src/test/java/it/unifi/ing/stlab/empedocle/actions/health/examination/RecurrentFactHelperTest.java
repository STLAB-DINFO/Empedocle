package it.unifi.ing.stlab.empedocle.actions.health.examination;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.visitor.fact.tools.EmptyFactVisitor;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactCopyVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignStatusVisitor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecurrentFactHelperTest extends BasicFactTest {

	private RecurrentFactHelper helper;
	
	@Mock private ExaminationDao examinationDao;
	@Mock private Patient fakePatient;
	
	@Before
	public void setUp() {
		super.setUp();
		helper = new RecurrentFactHelper(examinationDao);
		when(fakePatient.getId()).thenReturn(1L);
		
	}
	
	// recupero di una figlia testuale
	@Test
	public void testResumeRecurrentFactSimple() {
		TextualFact fake_return = mng.createTextual(author, time);
		fake_return.setStatus(FactStatus.ACTIVE);
		fake_return.assignType(ttxt2);
		fake_return.setText("recuperata!");
		fake_return.setContext(context);
		
		when(examinationDao.resume(any(Fact.class), any(Patient.class))).thenReturn(fake_return);
		
		helper.resumeRecurrentFacts(root);
		
		assertEquals(1, root.listChildren().size());
		Fact first = root.listChildren().iterator().next().getTarget();
		
		assertEquals(2, first.listChildren().size());
		
		assertEquals("recuperata!", ((TextualFact)first.listChildrenOrdered().get(0).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(0).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(0).getTarget().getContext());
		assertTrue(child_txt2 == first.listChildrenOrdered().get(0).getTarget());
		
		assertEquals(context, first.listChildrenOrdered().get(1).getTarget().getContext());
		assertTrue(child_txt1 == first.listChildrenOrdered().get(1).getTarget());

	}
	
	@Test
	public void testResumeRecurrentFactsComposite() {
		cmp.getType().setRecurrent(true);
		child_txt2.getType().setRecurrent(false);
		
		Fact destination = createEmptyCopy(root);
		
		Examination e = (Examination)root.getContext();
		Appointment a = AppointmentFactory.createAppointment();
		a.setPatient(fakePatient);
		e.setAppointment(a);
		
		AssignContextVisitor acv = new AssignContextVisitor(e);
		destination.accept(acv);
		AssignStatusVisitor asv = new AssignStatusVisitor(FactStatus.DRAFT);
		destination.accept(asv);
		
		when(examinationDao.resume(any(Fact.class), any(Patient.class))).thenReturn(cmp);
		
		helper.resumeRecurrentFacts(destination);
		
		// copia di cmp
		Fact first = destination.listChildren().iterator().next().getTarget();
		
		assertEquals(2, first.listChildren().size());
		
		// copia di child_txt2
		assertEquals("testo2", ((TextualFact)first.listChildrenOrdered().get(0).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(0).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(0).getTarget().getContext());
		assertTrue(child_txt2 != first.listChildrenOrdered().get(0).getTarget());
		
		assertEquals("testo1", ((TextualFact)first.listChildrenOrdered().get(1).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(1).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(1).getTarget().getContext());
		assertTrue(child_txt1 != first.listChildrenOrdered().get(1).getTarget());
		
	}
	
	private Fact createEmptyCopy(Fact src) {
		FactCopyVisitor cv = new FactCopyVisitor();
		src.accept(cv);
		Fact result = cv.getResult();
		
		EmptyFactVisitor ev = new EmptyFactVisitor();
		result.accept(ev);
		
		for(FactLink fl : result.listChildren()) {
			assertTrue(fl.getTarget().isEmpty());
		}
		
		return result;
		
	}
	
}
