package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;

import org.junit.Test;

public class ExaminationJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		ExaminationType type = ExaminationTypeFactory.createExaminationType();
		type.setName( "Examination type" );
		entityManager.persist( type );

		Appointment appointment = AppointmentFactory.createAppointment();
		appointment.setDate( new Date() );
		appointment.setNumber( "1234" );
		appointment.setStatus( AppointmentStatus.BOOKED );
		entityManager.persist( appointment );
		
		User user = UserFactory.createUser();
		user.setName( "Fabio" );
		user.setSurname( "Mori" );
		user.setUserid( "fabio.mori" );
		entityManager.persist( user );
		
		Examination examination = ExaminationFactory.createExamination();
		examination.setAppointment( appointment );
		examination.setType( type );
		examination.setAuthor( user );
		examination.setStatus( ExaminationStatus.RUNNING );
		
		entityManager.persist( examination );

		uuid = examination.getUuid();
	}
	
	@Test
	public void testExamination() {
		Examination examination = (Examination)
			entityManager
				.createQuery( 
					"select p " +
					" from Examination p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( examination );
		assertNotNull( examination.getAppointment() );
		assertNotNull( examination.getType() );
		assertNotNull( examination.getAuthor() );
		assertEquals( ExaminationStatus.RUNNING, examination.getStatus() );
	}
	
}
