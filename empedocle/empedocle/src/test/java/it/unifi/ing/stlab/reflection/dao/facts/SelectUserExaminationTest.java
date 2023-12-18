package it.unifi.ing.stlab.reflection.dao.facts;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class SelectUserExaminationTest extends PersistenceTest {
	
	private Examination e1;
	private FactImpl root1;
	private User author;
	private Time time;
	
	@Override
	public void insertData() {
		FactManager mng = new FactManager();
		
		author = UserFactory.createUser();
		author.setUserid("admin");
		author.setSurname("admin");
		entityManager.persist(author);
		
		time = new Time(Calendar.getInstance().getTime());
		
		Service s = ServiceFactory.createService();
		entityManager.persist(s);
		
		Appointment a = AppointmentFactory.createAppointment();
		a.addService(s);
		entityManager.persist(a);
		
		ExaminationType et = ExaminationTypeFactory.createExaminationType();
		entityManager.persist(et);
		
		Type t = TypeFactory.createCompositeType();
		entityManager.persist(t);
		
		
		e1 = ExaminationFactory.createExamination();
		e1.setStatus(ExaminationStatus.SUSPENDED);
		e1.setAppointment(a);
		e1.setType(et);
		entityManager.persist(e1);
		
		root1 = mng.createComposite(author, time);
		root1.setContext(e1);
		entityManager.persist(root1.getOrigin());
		entityManager.persist(root1);
		
		root1.assignType(t);
		et.setType(t);
		
		Examination e2 = ExaminationFactory.createExamination();
		e2.setStatus(ExaminationStatus.CONCLUDED);
		e2.setAppointment(a);
		e2.setType(et);
		entityManager.persist(e2);
		
		FactImpl root2 = mng.createComposite(author, time);
		root2.setContext(e2);
		entityManager.persist(root2.getOrigin());
		entityManager.persist(root2);
		
		root2.assignType(t);
		
		Examination e3 = ExaminationFactory.createExamination();
		e3.setStatus(ExaminationStatus.SUSPENDED);
		e3.setAppointment(a);
		e3.setType(et);
		entityManager.persist(e3);
		
		User other = UserFactory.createUser();
		other.setUserid("other");
		other.setSurname("other");
		entityManager.persist(other);
		
		FactImpl root3 = mng.createComposite(other, time);
		root3.setContext(e3);
		entityManager.persist(root3.getOrigin());
		entityManager.persist(root3);
		
	}
	
	@Test
	public void testQuery() {
		
		List<?> result2 = entityManager.createQuery("from Examination e").getResultList();
		assertEquals(3, result2.size());
		
		String q = "select distinct(e)" +
				" from Examination e, FactImpl f" +
//				" join e.appointment.services s" +   //XXX non serve
				" join f.context c" +
				" where c.id = e.id" +
				" and f.type = e.type.type" +
				" and f.destination is null" +
				" and f.origin.author.surname like 'admin'" +
				" and e.status = 'SUSPENDED'";
		
		List<?> result = entityManager.createQuery(q).getResultList();
		assertEquals(1, result.size());
		
	}

	
	
}
