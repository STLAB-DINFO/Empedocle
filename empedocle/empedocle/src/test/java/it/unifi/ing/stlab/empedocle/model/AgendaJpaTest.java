package it.unifi.ing.stlab.empedocle.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class AgendaJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override 
	protected void insertData() throws Exception {
		Agenda agenda = AgendaFactory.createAgenda();
		agenda.setCode( "code" );
		agenda.setDescription( "description" );
		
		entityManager.persist( agenda );

		uuid = agenda.getUuid();
	}
	
	@Test
	public void testAgenda() {
		Agenda agenda = (Agenda)
			entityManager
				.createQuery( 
					"select p " +
					" from Agenda p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( agenda );
		
		assertEquals( "code", agenda.getCode());
		assertEquals( "description", agenda.getDescription());
	}
	
}
