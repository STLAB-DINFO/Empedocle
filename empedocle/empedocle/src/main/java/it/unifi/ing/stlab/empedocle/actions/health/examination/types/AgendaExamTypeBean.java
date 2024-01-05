package it.unifi.ing.stlab.empedocle.actions.health.examination.types;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;

import javax.persistence.EntityManager;

public class AgendaExamTypeBean {

	private Agenda agenda;
	private final ExaminationType current;
	private final EntityManager entityManager;

	public AgendaExamTypeBean( ExaminationType current, EntityManager entityManager ) {
		this.current = current;
		this.entityManager = entityManager;
	}

	public AgendaExamTypeBean( Agenda agenda, ExaminationType current, EntityManager entityManager ) {
		this.agenda = agenda;
		this.current = current;
		this.entityManager = entityManager;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda( Agenda agenda ) {
		agenda.setExaminationType( current );
		this.agenda = entityManager.merge( agenda );
	}

	public void clear() {
		if ( agenda == null )
			return;

		agenda.setExaminationType( null );
		entityManager.merge( agenda );
		agenda = null;
	}

}
