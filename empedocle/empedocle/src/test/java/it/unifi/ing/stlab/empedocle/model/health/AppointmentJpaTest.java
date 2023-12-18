package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import java.util.Date;

import org.junit.Test;

public class AppointmentJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		Appointment appointment = AppointmentFactory.createAppointment();
		appointment.setDate( new Date() );
		appointment.setNumber( "number" );
		appointment.setBookingCode( "bookingCode" );
		appointment.setAcceptanceCode( "acceptanceCode" );
		appointment.setStatus( AppointmentStatus.BOOKED );
		
		entityManager.persist( appointment );

		uuid = appointment.getUuid();
	}
	
	@Test
	public void testAppointment() {
		Appointment appointment = (Appointment)
			entityManager
				.createQuery( 
					"select p " +
					" from Appointment p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( appointment );
		
		assertNotNull( appointment.getDate() );
		assertEquals( "number", appointment.getNumber());
		assertEquals( "bookingCode", appointment.getBookingCode());
		assertEquals( "acceptanceCode", appointment.getAcceptanceCode());
		assertEquals( AppointmentStatus.BOOKED, appointment.getStatus() );
	}
	
}
