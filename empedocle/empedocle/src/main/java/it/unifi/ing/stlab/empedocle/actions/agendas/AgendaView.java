package it.unifi.ing.stlab.empedocle.actions.agendas;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;

@Named
@ViewScoped
public class AgendaView implements Serializable {

	private static final long serialVersionUID = 3458218808438506412L;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;

	//
	// EJB injections
	//
	@Inject
	private AgendaDao agendaDao;

	//
	// Local attributes
	//
	private Agenda current;

	@PostConstruct
	public void init() {
		current = agendaDao.findById( Long.parseLong( id ) );
	}

	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}

	public Agenda getCurrent() {
		return current;
	}

}
