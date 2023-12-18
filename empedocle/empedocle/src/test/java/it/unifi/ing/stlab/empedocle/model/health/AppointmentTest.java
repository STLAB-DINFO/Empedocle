package it.unifi.ing.stlab.empedocle.model.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.factory.health.AppointmentFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;

import org.junit.Before;
import org.junit.Test;

public class AppointmentTest {

	protected Appointment appointment;
	
	@Before
	public void setUp() {
		appointment = AppointmentFactory.createAppointment();
	}
	
	@Test
	public void testListServices() {
		assertEquals( 0, appointment.listServices().size() );
	}
	
	@Test
	public void testAddService() {
		Service agenda = ServiceFactory.createService();
		appointment.addService( agenda );

		assertEquals( 1, appointment.listServices().size() );
		assertTrue( appointment.listServices().contains( agenda ));
		
	}

	@Test
	public void testAddServiceNull() {
		appointment.addService( null );
		assertEquals( 0, appointment.listServices().size() );
	}

	@Test
	public void testRemoveService() {
		Service agenda = ServiceFactory.createService();
		appointment.addService( agenda );
		appointment.removeService( agenda );

		assertEquals( 0, appointment.listServices().size() );
	}

	@Test
	public void testClearServices() {
		appointment.addService( ServiceFactory.createService() );
		appointment.clearServices();

		assertEquals( 0, appointment.listServices().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddService() {
		appointment.listServices().add( ServiceFactory.createService() );
	}

}
