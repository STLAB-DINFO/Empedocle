package it.unifi.ing.stlab.empedocle.model.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import it.unifi.ing.stlab.empedocle.factory.MessageFactory;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.empedocle.model.messages.MessageLevel;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class MessageJpaTest extends PersistenceTest {

	protected String uuid;
	private Patient patient;

	@Override
	protected void insertData() throws Exception {
		patient = PatientFactory.createPatient();

		Message message = MessageFactory.createMessage();
		message.setDate( new Date( 1000 ) );
		message.setSender( "me" );
		message.setLevel( MessageLevel.INFO );
		message.setSubject( "subject message" );
		message.setBody( "body message" );
		message.setPatient( patient );

		entityManager.persist( patient );
		entityManager.persist( message );

		uuid = message.getUuid();
	}

	@Test
	public void testMessage() {
		Message message = (Message) entityManager
				.createQuery( "select p " + " from Message p " + " where p.uuid = :uuid" )
				.setParameter( "uuid", uuid ).getSingleResult();

		assertNotNull( message );

		assertEquals( "me", message.getSender() );
		assertEquals( "subject message", message.getSubject() );
		assertEquals( "body message", message.getBody() );
		assertEquals( MessageLevel.INFO, message.getLevel() );
		assertEquals( new Date( 1000 ), message.getDate() );
		assertEquals( patient, message.getPatient() );
	}

}
