package it.unifi.ing.stlab.empedocle.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.StaffFactory;

import org.junit.Before;
import org.junit.Test;

public class StaffTest {

	protected Staff staff;
	
	@Before
	public void setUp() {
		staff = StaffFactory.createStaff();
	}
	
	
	//
	// Roles
	//
	
	@Test
	public void testListAgendas() {
		assertEquals( 0, staff.listAgendas().size() );
	}
	
	@Test
	public void testAddAgenda() {
		Agenda agenda = AgendaFactory.createAgenda();
		staff.addAgenda( agenda );

		assertEquals( 1, staff.listAgendas().size() );
		assertTrue( staff.listAgendas().contains( agenda ));
		
	}

	@Test
	public void testAddAgendaNull() {
		staff.addAgenda( null );
		assertEquals( 0, staff.listAgendas().size() );
	}

	@Test
	public void testRemoveAgenda() {
		Agenda agenda = AgendaFactory.createAgenda();
		staff.addAgenda( agenda );
		staff.removeAgenda( agenda );

		assertEquals( 0, staff.listAgendas().size() );
	}

	@Test
	public void testClearAgendas() {
		staff.addAgenda( AgendaFactory.createAgenda() );
		staff.clearAgendas();

		assertEquals( 0, staff.listAgendas().size() );
	}

	@Test( expected = UnsupportedOperationException.class )
	public void testNoDirectAddAgenda() {
		staff.listAgendas().add( AgendaFactory.createAgenda() );
	}
	
}
