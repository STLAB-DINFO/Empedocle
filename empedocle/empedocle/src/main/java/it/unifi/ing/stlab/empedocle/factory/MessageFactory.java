package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.messages.Message;

import java.util.UUID;

public class MessageFactory {

	public static Message createMessage() {
		return new Message( UUID.randomUUID().toString() );
	}
} 
