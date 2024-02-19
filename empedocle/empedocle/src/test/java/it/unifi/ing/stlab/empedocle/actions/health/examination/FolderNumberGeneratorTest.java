package it.unifi.ing.stlab.empedocle.actions.health.examination;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.view.controllers.cardiology.FolderNumberGenerator;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.PatientIdentifier;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class FolderNumberGeneratorTest extends PersistenceTest {
	
	private FolderNumberGenerator generator;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		generator = new FolderNumberGeneratorBean();
		FieldUtils.assignField(generator, "entityManager", entityManager);
	}
	
	@Test
	public void testGenerateFolderNumber() {
		Patient p = PatientFactory.createPatient();
		PatientIdentifier pi = PatientFactory.createPatientIdentifier();
		pi.setCode( "paziente" );
		p.setIdentifier(pi);
		
		entityManager.persist(pi);
		entityManager.persist(p);
		
		Appointment a = AppointmentFactory.createAppointment();
		a.setNumber("1234");
		a.setPatient(p);
		entityManager.persist(a);
		
		Examination e = ExaminationFactory.createExamination();
		e.setAppointment(a);
		entityManager.persist(e);
		
		QuantitativeType t = TypeFactory.createQuantitativeType();
		t.setName("Clinical record numbero");
		entityManager.persist(t);
		
		Unit u = UnitFactory.createUnit();
		entityManager.persist(u);
		
		entityManager.flush();
		entityManager.getTransaction().commit();
		
		
		// test 1
		entityManager.getTransaction().begin();
		
		QuantitativeFact f = FactFactory.createQuantitative();
		Quantity q = new Quantity(null, u);
		f.setQuantity(q);
		f.assignType(t);
		f.setContext(e);
		f.setStatus(FactStatus.DRAFT);
		
		entityManager.persist(f);
		entityManager.flush();
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		generator.generateFolderNumber(f);
		entityManager.getTransaction().commit();
		
		assertEquals(1, f.getQuantity().getValue().longValue());
		
		
		// test 2
		entityManager.getTransaction().begin();
		
		QuantitativeFact f2 = FactFactory.createQuantitative();
		Quantity q2 = new Quantity(10.0, u);
		f2.setQuantity(q2);
		f2.setContext(e);
		f2.assignType(t);
		f2.setStatus(FactStatus.DRAFT);
		entityManager.persist(f2);
		
		QuantitativeFact f3 = FactFactory.createQuantitative();
		Quantity q3 = new Quantity(null, u);
		f3.setQuantity(q3);
		f3.setContext(e);
		f3.assignType(t);
		f3.setStatus(FactStatus.DRAFT);
		entityManager.persist(f3);
		entityManager.flush();
		entityManager.getTransaction().commit();
		
		entityManager.getTransaction().begin();
		generator.generateFolderNumber(f3);
		entityManager.getTransaction().commit();
		
		assertEquals(11, f3.getQuantity().getValue().longValue());
		
		
	}

}
