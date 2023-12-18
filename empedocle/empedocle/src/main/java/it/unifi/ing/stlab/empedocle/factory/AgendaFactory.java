package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.Agenda;

import java.util.UUID;

public class AgendaFactory {

	public static Agenda createAgenda() {
		return new Agenda( UUID.randomUUID().toString() );
	}
} 
